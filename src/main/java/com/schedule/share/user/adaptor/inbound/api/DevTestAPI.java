package com.schedule.share.user.adaptor.inbound.api;

import com.schedule.share.common.util.JwtUtil;
import com.schedule.share.user.adaptor.inbound.api.dto.TokenResponseDTO;
import com.schedule.share.user.adaptor.inbound.api.mapper.SocialLoginDTOMapper;
import com.schedule.share.user.adaptor.inbound.api.mapper.UserDTOMapper;
import com.schedule.share.user.adaptor.outbound.UserCommandAdaptor;
import com.schedule.share.user.application.port.inbound.LoginServiceUseCase;
import com.schedule.share.user.application.port.inbound.TokenServiceUseCase;
import com.schedule.share.user.application.port.outbound.TokenCommandPort;
import com.schedule.share.user.application.service.user.UserWriter;
import com.schedule.share.user.domain.RefreshToken;
import com.schedule.share.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DevTestAPI {

    private static final Logger log = LoggerFactory.getLogger(DevTestAPI.class);
    private final JwtUtil jwtUtil;
    private final UserCommandAdaptor userCommandAdaptor;
    private final TokenCommandPort tokenCommandPort;


    @GetMapping(value = "/create-user")
    public TokenResponseDTO.AccessAndRefreshToken signup() {
        log.info("Sign up");
        Random rand = new Random();
        char ri1 = (char) (rand.nextInt(26) + 97);
        char ri2 = (char) (rand.nextInt(26) + 97);
        char ri3 = (char) (rand.nextInt(26) + 97);
        char ri4 = (char) (rand.nextInt(26) + 97);

        String nick = "test"+rand.nextInt(100);

        byte im1 = (byte) rand.nextInt(10);
        byte im2 = (byte) rand.nextInt(10);
        byte im3 = (byte) rand.nextInt(10);


        String rndCi = String.valueOf(new char[]{ri1, ri2, ri3, ri4});
        User test = User.builder().method("naver")
                .image(new byte[]{im1, im2, im3})
                .ci(rndCi)
                .nickname(nick)
                .build();

        long testUserId = userCommandAdaptor.create(test);
        String acc = jwtUtil.generateAccessToken(testUserId);
        String ref = jwtUtil.generateRefreshToken(testUserId);
        RefreshToken refresh = RefreshToken.builder()
                .userId(testUserId)
                .ci(rndCi)
                .refreshToken(ref)
                .build();
        tokenCommandPort.createRefreshToken(refresh);
        TokenResponseDTO.AccessAndRefreshToken resTok = TokenResponseDTO.AccessAndRefreshToken.builder()
                .accessToken(acc)
                .refreshToken(ref)
                .build();

        return resTok;
    }
}
