package fit.hcmute.JobIT.config;

import fit.hcmute.JobIT.entity.Permission;
import fit.hcmute.JobIT.entity.Role;
import fit.hcmute.JobIT.entity.User;
import fit.hcmute.JobIT.exception.PermissionDeniedException;
import fit.hcmute.JobIT.service.UserService;
import fit.hcmute.JobIT.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;




public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler){

        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);

        String email = SecurityUtil.getCurrentUserLogin().orElse("");

        if (!email.isEmpty() && !email.equals("anonymousUser")) {
            User user = userService.getUserByEmail(email);
            if (user == null || user.getRole() == null) {
                throw new PermissionDeniedException("Access denied: user or role not found.");
            }

            boolean isAllowed = user.getRole().getPermissions().stream()
                    .anyMatch(permission ->
                            permission.getApiPath().equals(path)
                                    && permission.getMethod().equalsIgnoreCase(httpMethod));

            if (!isAllowed) {
                throw new PermissionDeniedException("Access denied: insufficient permissions for path " + path);
            }
        }

        return true;
    }
}
