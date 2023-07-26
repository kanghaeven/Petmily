//package com.pjt.petmily.global.oauth2.service;
//
//import com.pjt.petmily.domain.user.repository.UserRepository;
//import com.pjt.petmily.global.oauth2.OAuthAttributes;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.Objects;
//
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    private final UserRepository userRepository;
//    private final CustomAuthorityUtils authorityUtils;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = service.loadUser(userRequest);  // OAuth2 정보를 가져옵니다.
//
//        Map<String, Object> originAttributes = oAuth2User.getAttributes();  // OAuth2User의 attribute
//
//        // OAuth2 서비스 id (google, kakao, naver)
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 소셜 정보를 가져옵니다.
//
//        // OAuthAttributes: OAuth2User의 attribute를 서비스 유형에 맞게 담아줄 클래스
//        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);
//        User user = saveOrUpdate(attributes);
//        String email = user.getEmail();
//        List<GrantedAuthority> authorities = authorityUtils.createAuthorities(email);
//
//        return new OAuth2CustomUser(registrationId, originAttributes, authorities, email);
//    }
//
//    /**
//     * 이미 존재하는 회원이라면 이름과 프로필이미지를 업데이트해줍니다.
//     * 처음 가입하는 회원이라면 User 테이블을 생성합니다.
//     **/
//    private User saveOrUpdate(OAuthAttributes authAttributes) {
//        User user = userRepository.findByEmail(authAttributes.getEmail())
//                .map(entity -> entity.update(authAttributes.getName(), authAttributes.getProfileImageUrl()))
//                .orElse(authAttributes.toEntity());
//
//        return userRepository.save(user);
//    }
//}
