package fit.hcmute.JobIT.controller;

import fit.hcmute.JobIT.dto.request.LoginRequest;
import fit.hcmute.JobIT.dto.response.LoginResponse;
import fit.hcmute.JobIT.entity.User;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.service.UserService;
import fit.hcmute.JobIT.util.SecurityUtil;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import fit.hcmute.JobIT.util.property.JwtProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final JwtProperties jwtProperties;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        //xác thực người dùng => cần viết hàm loadUserByUsername

        //Nếu xác thực thành công, trả về đối tượng Authentication
        //AuthenticationManager sẽ sử dụng UserDetailsService để lấy thông tin người dùng
        //Có thể kế thừa UserDetailsService để lấy thm thông tin người dùng từ cơ sở dữ liệu
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //Lưu thông tin xác thực vào SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Trả về response
        LoginResponse loginResponse = new LoginResponse();
        User currentUser = userService.getUserByEmail(loginRequest.getUsername());

        if (currentUser != null) {
            LoginResponse.UserLoginResponse userLoginResponse = new LoginResponse.UserLoginResponse(currentUser.getId(),
                    currentUser.getName(), currentUser.getEmail());
            loginResponse.setUser(userLoginResponse);
        }

        String access_token = securityUtil.createAccessToken(authentication.getName(), loginResponse.getUser());

        loginResponse.setAccessToken(access_token);

        // Tạo refresh token và lưu vào cơ sở dữ liệu
        String refreshToken = securityUtil.createRefreshToken(loginRequest.getUsername(), loginResponse);
        userService.updateUserToken(loginRequest.getUsername(), refreshToken);

        // Set cookie cho refresh token
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true) // Chỉ sử dụng qua HTTPS
                .path("/") // Đặt đường dẫn cookie
                .maxAge(jwtProperties.getRefreshTokenExpiration())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginResponse);
    }

    @GetMapping("/account")
    @ApiMessage("Get account information")
    public ResponseEntity<LoginResponse.UserLoginResponse> getAccount() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
                SecurityUtil.getCurrentUserLogin().get() : "Anonymous";

        User currentUser = userService.getUserByEmail(email);

        LoginResponse.UserLoginResponse userLogin = new LoginResponse.UserLoginResponse();
        if (currentUser != null) {
            userLogin.setId(currentUser.getId());
            userLogin.setName(currentUser.getName());
            userLogin.setEmail(currentUser.getEmail());
        }
        return ResponseEntity.ok().body(userLogin);
    }

    @GetMapping("/refresh")
    @ApiMessage("Get user information from refresh token")
    public ResponseEntity<LoginResponse> getRefreshToken(
            @CookieValue(value = "refresh_token") String refreshToken
    ) {
        // Kiểm tra xem refresh token có hợp lệ không
        Jwt decodedToken = securityUtil.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject();

        // Check User by email + refresh token
        User currentUser = userService.findByRefreshTokenAndEmail(refreshToken, email);


        // issue new token/ set refresh token as cookie
        LoginResponse loginResponse = new LoginResponse();
        User currentUserDB = userService.getUserByEmail(email);

        if (currentUserDB != null) {
            LoginResponse.UserLoginResponse userLoginResponse = new LoginResponse.UserLoginResponse(
                    currentUserDB.getId(),
                    currentUserDB.getName(),
                    currentUserDB.getEmail());
            loginResponse.setUser(userLoginResponse);
        }

        String access_token = securityUtil.createAccessToken(email, loginResponse.getUser());

        loginResponse.setAccessToken(access_token);

        // Tạo refresh token và lưu vào cơ sở dữ liệu
        String newRefreshToken = securityUtil.createRefreshToken(email, loginResponse);
        userService.updateUserToken(email, newRefreshToken);

        // Set cookie cho refresh token
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true) // Chỉ sử dụng qua HTTPS
                .path("/") // Đặt đường dẫn cookie
                .maxAge(jwtProperties.getRefreshTokenExpiration())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginResponse);
    }

    @GetMapping("/logout")
    @ApiMessage("Logout user and clear refresh token cookie")
    public ResponseEntity<Void> logout() {

        // Xoá refresh token trong cơ sở dữ liệu
        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(()
                -> new IdInvalidException("User not authenticated"));
        userService.updateUserToken(email, null);

        // Tạo cookie rỗng để xoá cookie refresh_token
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true) // Chỉ sử dụng qua HTTPS
                .path("/") // Đặt đường dẫn cookie
                .maxAge(0) // Đặt thời gian sống của cookie là 0 để xoá nó
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }
}
