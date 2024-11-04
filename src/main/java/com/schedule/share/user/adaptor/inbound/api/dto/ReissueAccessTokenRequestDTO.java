package com.schedule.share.user.adaptor.inbound.api.dto;

import lombok.Builder;

public class ReissueAccessTokenRequestDTO {
    @Builder
    public record RefreshToken(
        String refreshToken
    ){}
}
