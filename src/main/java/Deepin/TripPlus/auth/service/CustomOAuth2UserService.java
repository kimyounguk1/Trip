package Deepin.TripPlus.auth.service;

import Deepin.TripPlus.auth.dto.CustomOAuth2User;
import Deepin.TripPlus.auth.dto.KakaoResponse;
import Deepin.TripPlus.auth.dto.KakaoUser;
import Deepin.TripPlus.auth.dto.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override//OAuth2User로 반환해야 함
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //서버로부터 access token을 이용해 유저 정보를 받아옵니다.
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User);
        //spring.security.oauth2.client.registration.kakao.client-name=Kakao
        //로그인 정보 제공자
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("kakao")) { //카카오면 실행 로직
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes()); //oAuth2User의 응답을 attribute를 받아 객체 생성
            //기존 정보를 사용하기 쉽게 변환
        }else{
            return null;
        }

        KakaoUser userDTO = new KakaoUser();
        //중복이 없을때 조건 넣기
        userDTO.setEmail(oAuth2Response.getEmail());
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole("ROLE_USER");
        //
        return new CustomOAuth2User(userDTO);
    }
}
