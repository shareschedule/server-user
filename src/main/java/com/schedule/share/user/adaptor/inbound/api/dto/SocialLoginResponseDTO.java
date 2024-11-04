package com.schedule.share.user.adaptor.inbound.api.dto;

import com.schedule.share.user.application.service.user.vo.UserVO;
import lombok.Builder;

public class SocialLoginResponseDTO {

    @Builder
    public record Response(
            String accessToken,
            String refreshToken,
            UserResponseDTO.Response user
    ) {
    }
}
