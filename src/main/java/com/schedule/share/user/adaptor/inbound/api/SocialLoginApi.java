package com.schedule.share.user.adaptor.inbound.api;

import com.schedule.share.common.model.ResponseModel;
import com.schedule.share.user.adaptor.inbound.api.dto.SocialLoginRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.SocialLoginResponseDTO;
import com.schedule.share.user.adaptor.inbound.api.mapper.SocialLoginDTOMapper;
import com.schedule.share.user.adaptor.inbound.api.mapper.UserDTOMapper;
import com.schedule.share.user.application.port.inbound.LoginServiceUseCase;
import com.schedule.share.user.application.service.user.vo.SocialLoginVO;
import com.schedule.share.user.application.service.user.vo.UserVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인", description = "로그인 API")
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class SocialLoginApi {

    private final LoginServiceUseCase<SocialLoginVO.NaverOauthCredential, UserVO.Save> loginServiceUseCase;
    private final SocialLoginDTOMapper socialLoginDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @PostMapping(value = "/naver/user")
    public ResponseModel<SocialLoginResponseDTO.Response> signup(@RequestBody SocialLoginRequestDTO.Signup signup) {
        SocialLoginVO.NaverOauthCredential naverOauthVO = socialLoginDTOMapper.toVO(signup.naverOauthCredential());

        UserVO.Save userVO = userDTOMapper.toVO(signup.userInfo());
        SocialLoginVO.Token token = loginServiceUseCase.sign(naverOauthVO, userVO);

        SocialLoginResponseDTO.Response response = socialLoginDTOMapper.toResponse(token);
//        UserResponseDTO.Response responseDTO = userDTOMapper.toResponseDTO(token.user());
//        setTokenCookie(response, token);

        return ResponseModel.of(response);
    }

    @PostMapping(value = "/naver")
    public ResponseModel<SocialLoginResponseDTO.Response> naverLogin(@RequestBody SocialLoginRequestDTO.NaverOauthCredential naverOauthCredential) {
        SocialLoginVO.Token token = loginServiceUseCase.login(socialLoginDTOMapper.toVO(naverOauthCredential));

//        UserResponseDTO.Response responseDTO = userDTOMapper.toResponseDTO(token.user());
//        setTokenCookie(response, token);
        SocialLoginResponseDTO.Response response = socialLoginDTOMapper.toResponse(token);
        return ResponseModel.of(response);
    }

    private void setTokenCookie(HttpServletResponse response, SocialLoginVO.Token token) {
        Cookie accessCookie = createHttpOnlyCookie("accessToken", token.accessToken(), 0, 1);
        Cookie refreshCookie = createHttpOnlyCookie("refreshToken", token.accessToken(), 1, 0);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }

    private Cookie createHttpOnlyCookie(String key, String value, int day, int hour) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        int age = 60 * 60 * (hour + 24*day);
        cookie.setMaxAge(age);
        cookie.setPath("/");

        return cookie;
    }
}
