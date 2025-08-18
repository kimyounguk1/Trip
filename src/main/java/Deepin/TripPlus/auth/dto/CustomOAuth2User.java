package Deepin.TripPlus.auth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final KakaoUser kakaoUser;

    public CustomOAuth2User(final KakaoUser kakaoUser) {
        this.kakaoUser = kakaoUser;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return kakaoUser.getRole();
            }
        });
        return authorities;
    }

    @Override
    public String getName() {
        return kakaoUser.getName();
    }
}
