package flab.schoolreunion.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    public static final String TOKEN_START_TEXT = "Bearer ";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    public JwtFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        //임시 해제
        filterChain.doFilter(request, response);

//        String jwt = resolveToken(request);
//
//        TokenValidState tokenValidState = jwtTokenProvider.validateToken(jwt);
//
//        if (tokenValidState == TokenValidState.EXPIRED) {
//            response.getWriter().write("EXPIRED_TOKEN");
//        } else if (tokenValidState == TokenValidState.INVALID) {
//            response.getWriter().write("INVALID_TOKEN");
//        } else if (tokenValidState == TokenValidState.VALIDATED) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.info("Security Context에 '{}' 인증 정보를 저장했습니다. uri: {}", authentication.getName(), requestURI);
//        }
//        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_START_TEXT)) {
            return bearerToken.substring(TOKEN_START_TEXT.length());
        }
        return null;
    }
}
