package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.infra.rdb.entity.RefreshTokenEntity;
import com.schedule.share.infra.rdb.repository.RefreshTokenRepository;
import com.schedule.share.user.application.port.outbound.TokenQueryPort;
import com.schedule.share.user.domain.RefreshToken;
import com.schedule.share.user.domain.mapper.TokenMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class TokenQueryAdaptor implements TokenQueryPort {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenMapper tokenMapper;

    @Override
    @Transactional(readOnly = true)
    public RefreshToken getRefreshTokenByUserId(long userId) {

        RefreshTokenEntity refreshTokenByUserId = refreshTokenRepository.findByUserId(userId).orElseThrow();

        return tokenMapper.refreshTokenEntityToDomain(refreshTokenByUserId);
    }
}
