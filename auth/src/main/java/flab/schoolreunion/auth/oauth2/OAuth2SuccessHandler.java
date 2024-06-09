package flab.schoolreunion.auth.oauth2;

import flab.schoolreunion.auth.entity.RefreshToken;
import flab.schoolreunion.auth.jwt.JwtTokenProvider;
import flab.schoolreunion.auth.repository.RefreshTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public OAuth2SuccessHandler(JwtTokenProvider tokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.tokenProvider = tokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = tokenProvider.createToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        String email = authentication.getName();

        RefreshToken refreshTokenEntity;

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findRefreshTokenByEmail(email);

        //이미 RefreshToken이 있으면 갱신, 없으면 발급
        if (refreshTokenOptional.isPresent()) {
            refreshTokenEntity = refreshTokenOptional.get();
            refreshTokenEntity.update(refreshToken);
        } else {
            refreshTokenEntity = RefreshToken.builder()
                    .refreshToken(refreshToken)
                    .email(email)
                    .build();
            refreshTokenRepository.save(refreshTokenEntity);
        }

        response.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + token);

        Cookie cookie = new Cookie("refresh_token", refreshToken);
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        //TODO: response
        response.sendRedirect("/");
    }
}
