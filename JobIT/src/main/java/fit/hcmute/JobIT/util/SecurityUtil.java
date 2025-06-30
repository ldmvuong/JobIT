package fit.hcmute.JobIT.util;

import com.nimbusds.jose.util.Base64;

import fit.hcmute.JobIT.dto.response.LoginResponse;
import fit.hcmute.JobIT.util.property.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SecurityUtil {

    private final JwtEncoder jwtEncoder;

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    private final JwtProperties jwtProperties;

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtProperties.getKey()).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }

    public String createAccessToken(String email, LoginResponse user) {

        LoginResponse.UserInsideToken userInsideToken = new LoginResponse.UserInsideToken(
                user.getUser().getId(),
                user.getUser().getName(),
                user.getUser().getEmail()
        );

        Instant now = Instant.now();
        Instant validity = now.plus(jwtProperties.getTokenExpiration(), ChronoUnit.SECONDS);

        List<String> listAuthorities = new ArrayList<>();

        listAuthorities.add("ROLE_USER");
        listAuthorities.add("ROLE_ADMIN");

        // @formatter:off
        //body
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email) // Lưu ý: subject có thể là email hoặc id của người dùng
                .claim("user", userInsideToken) // Thêm thông tin người dùng vào claims
                .claim("permission", listAuthorities) // Thêm quyền của người dùng vào claims
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String createRefreshToken(String email, LoginResponse loginResponse) {
        Instant now = Instant.now();
        Instant validity = now.plus(jwtProperties.getRefreshTokenExpiration(), ChronoUnit.SECONDS);

        LoginResponse.UserInsideToken userInsideToken = new LoginResponse.UserInsideToken(
                loginResponse.getUser().getId(),
                loginResponse.getUser().getName(),
                loginResponse.getUser().getEmail()
        );

        // @formatter:off
        //body
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email) // Lưu ý: subject có thể là email hoặc id của người dùng
                .claim("user", userInsideToken)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public Jwt checkValidRefreshToken(String token) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM).build();
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                System.out.println(">>> Refresh token error: " + e.getMessage());
                throw e;
            }
    }
    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getSubject();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
//    public static boolean isAuthenticated() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication != null && getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
//    }

    /**
     * Checks if the current user has any of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has any of the authorities, false otherwise.
     */
    public static boolean hasCurrentUserAnyOfAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (
                authentication != null && getAuthorities(authentication).anyMatch(authority -> Arrays.asList(authorities).contains(authority))
        );
    }

    /**
     * Checks if the current user has none of the authorities.
     *
     * @param authorities the authorities to check.
     * @return true if the current user has none of the authorities, false otherwise.
     */
    public static boolean hasCurrentUserNoneOfAuthorities(String... authorities) {
        return !hasCurrentUserAnyOfAuthorities(authorities);
    }

    /**
     * Checks if the current user has a specific authority.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean hasCurrentUserThisAuthority(String authority) {
        return hasCurrentUserAnyOfAuthorities(authority);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }
}
