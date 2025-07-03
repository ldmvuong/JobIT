package fit.hcmute.JobIT.controller;

import fit.hcmute.JobIT.dto.request.user.LoginRequest;
import fit.hcmute.JobIT.dto.response.LoginResponse;
import fit.hcmute.JobIT.dto.response.user.CreateUserResponse;
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

/**
 * API đăng nhập người dùng.
 * Nếu username/password hợp lệ, trả về access token và set refresh token vào cookie.
 */
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

        // 1. Tạo đối tượng chứa thông tin đăng nhập người dùng (username + password)
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());


        // 2. Gửi thông tin đến AuthenticationManager để xác thực người dùng
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. Nếu xác thực thành công, lưu thông tin xác thực vào SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. Lấy thông tin người dùng từ DB để đưa vào response
        LoginResponse loginResponse = new LoginResponse();
        User currentUser = userService.getUserByEmail(loginRequest.getUsername());

        if (currentUser != null) {
            LoginResponse.UserLoginResponse userLoginResponse = new LoginResponse.UserLoginResponse(
                    currentUser.getId(),
                    currentUser.getName(),
                    currentUser.getEmail(),
                    currentUser.getRole());
            loginResponse.setUser(userLoginResponse);
        }

        // 5. Tạo access token (JWT) và gán vào response
        String access_token = securityUtil.createAccessToken(authentication.getName(), loginResponse);
        loginResponse.setAccessToken(access_token);

        // 6. Tạo refresh token và lưu vào DB để phục vụ việc làm mới access token sau này
        String refreshToken = securityUtil.createRefreshToken(loginRequest.getUsername(), loginResponse);
        userService.updateUserToken(loginRequest.getUsername(), refreshToken);

        // 7. Gửi refresh token về phía client thông qua HttpOnly Cookie (bảo mật hơn localStorage)
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true) // Không thể đọc bằng JavaScript → tăng bảo mật
                .secure(true) // Chỉ sử dụng qua HTTPS
                .path("/") // Cookie có hiệu lực toàn bộ hệ thống
                .maxAge(jwtProperties.getRefreshTokenExpiration()) // Thời gian sống của cookie
                .build();

        // 8. Trả về login response kèm cookie
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginResponse);
    }

    /**
     * API lấy thông tin người dùng đang đăng nhập (từ access token).
     */
    @GetMapping("/account")
    @ApiMessage("Get account information")
    public ResponseEntity<LoginResponse.UserGetAccount> getAccount() {

        // 1. Lấy email của người dùng từ SecurityContext (nếu đã đăng nhập)
        String email = SecurityUtil.getCurrentUserLogin().orElse("Anonymous");

        // 2. Truy vấn thông tin người dùng từ DB
        User currentUser = userService.getUserByEmail(email);

        // 3. Chuẩn bị response với thông tin người dùng
        LoginResponse.UserLoginResponse userLogin = new LoginResponse.UserLoginResponse();
        LoginResponse.UserGetAccount userGetAccount = new LoginResponse.UserGetAccount();
        if (currentUser != null) {
            userLogin.setId(currentUser.getId());
            userLogin.setName(currentUser.getName());
            userLogin.setEmail(currentUser.getEmail());
            userLogin.setRole(currentUser.getRole());
            userGetAccount.setUser(userLogin);
        }
        return ResponseEntity.ok().body(userGetAccount);
    }


    /**
     * API làm mới access token bằng refresh token được lưu trong cookie.
     * Nếu hợp lệ → tạo mới access token + refresh token, cập nhật DB và cookie.
     */
    @GetMapping("/refresh")
    @ApiMessage("Get user information from refresh token")
    public ResponseEntity<LoginResponse> getRefreshToken(
            @CookieValue(value = "refresh_token") String refreshToken
    ) {

        // 1. Kiểm tra tính hợp lệ của refresh token (giải mã, kiểm tra thời hạn, chữ ký, subject)
        Jwt decodedToken = securityUtil.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject(); // Subject là email người dùng

        // 2. Kiểm tra refresh token và email có tồn tại đúng trong DB không
        User currentUser = userService.findByRefreshTokenAndEmail(refreshToken, email);

        // 3. Lấy thông tin người dùng để tạo lại token
        LoginResponse loginResponse = new LoginResponse();
        User currentUserDB = userService.getUserByEmail(email);

        if (currentUserDB != null) {
            LoginResponse.UserLoginResponse userLoginResponse = new LoginResponse.UserLoginResponse(
                    currentUserDB.getId(),
                    currentUserDB.getName(),
                    currentUserDB.getEmail(),
                    currentUserDB.getRole());
            loginResponse.setUser(userLoginResponse);
        }

        // 4. Tạo access token mới
        String access_token = securityUtil.createAccessToken(email, loginResponse);
        loginResponse.setAccessToken(access_token);

        // 5. Tạo refresh token mới và cập nhật vào DB
        String newRefreshToken = securityUtil.createRefreshToken(email, loginResponse);
        userService.updateUserToken(email, newRefreshToken);

        // 6. Gửi refresh token mới về qua cookie
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtProperties.getRefreshTokenExpiration())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(loginResponse);
    }

    /**
     * API đăng xuất người dùng:
     * - Xoá refresh token trong DB.
     * - Trả cookie rỗng để xoá ở phía client.
     */
    @PostMapping("/logout")
    @ApiMessage("Logout user and clear refresh token cookie")
    public ResponseEntity<Void> logout() {

        // 1. Lấy email người dùng hiện tại từ access token (nếu chưa đăng nhập thì ném lỗi)
        String email = SecurityUtil.getCurrentUserLogin().orElseThrow(()
                -> new IdInvalidException("User not authenticated"));

        // 2. Xoá refresh token trong DB bằng cách set null
        userService.updateUserToken(email, null);

        // 3. Tạo cookie rỗng để trình duyệt xoá cookie refresh_token
        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0) // 0 giây → trình duyệt sẽ xoá ngay cookie này
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @PostMapping("/register")
    @ApiMessage("Register a new user")
    public ResponseEntity<CreateUserResponse> register(@Valid @RequestBody User user) {


        // Tạo mới người dùng
        CreateUserResponse response = userService.createUser(user);

        return ResponseEntity.ok(response);
    }
}
