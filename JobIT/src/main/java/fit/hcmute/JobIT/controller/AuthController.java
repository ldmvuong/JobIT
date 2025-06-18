package fit.hcmute.JobIT.controller;

import fit.hcmute.JobIT.model.request.LoginRequest;
import fit.hcmute.JobIT.model.response.LoginResponse;
import fit.hcmute.JobIT.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        //xác thực người dùng => cần viết hàm loadUserByUsername
        //Nếu xác thực thành công, trả về đối tượng Authentication
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //Nếu xác thực thành công, tạo token
        String access_token = securityUtil.createToken(authentication);
        //Lưu thông tin xác thực vào SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Trả về response
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(access_token);
        return ResponseEntity.ok(loginResponse);
    }
}
