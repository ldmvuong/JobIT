package fit.hcmute.JobIT.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.hcmute.JobIT.model.response.RestResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    private final ObjectMapper mapper;

    public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {

        // Delegate to the default BearerTokenAuthenticationEntryPoint
        this.delegate.commence(request, response, authException);

        // Set the response status and content type VN
        response.setContentType("application/json;charset=UTF-8");

        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        String errorMessage = Optional.ofNullable(authException.getCause())
                        .map(Throwable :: getMessage)
                .orElse(authException.getMessage());

        restResponse.setError(errorMessage);
        restResponse.setMessage("Token không hợp lệ hoặc đã hết hạn");

        mapper.writeValue(response.getWriter(), restResponse);
    }
}