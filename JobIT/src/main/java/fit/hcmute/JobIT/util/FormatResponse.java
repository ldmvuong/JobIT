package fit.hcmute.JobIT.util;

import fit.hcmute.JobIT.dto.response.RestResponse;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class FormatResponse implements ResponseBodyAdvice<Object> {


    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {


        RestResponse<Object> restResponse = new RestResponse<Object>();

        // We can get the status code from the response
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();

        // Set the status code in the RestResponse
        restResponse.setStatusCode(status);

        if (body instanceof String) {
            return body;
        }

        if (status >= 400) {
            // If the response is an error, we set the error and message fields
            return body;
        } else {
            // If the response is successful, we set the data field
            restResponse.setData(body);
            restResponse.setMessage("Called API successfully");
        }
        return restResponse;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }
}
