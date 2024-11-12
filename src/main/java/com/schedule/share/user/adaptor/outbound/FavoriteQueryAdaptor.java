package com.schedule.share.user.adaptor.outbound;

import com.schedule.share.common.exception.Common404Exception;
import com.schedule.share.infra.rdb.entity.FavoriteEntity;
import com.schedule.share.infra.rdb.repository.FavoriteRepository;
import com.schedule.share.user.application.port.outbound.FavoriteQueryPort;
import com.schedule.share.user.domain.Favorite;
import com.schedule.share.user.domain.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FavoriteQueryAdaptor implements FavoriteQueryPort {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;

    @Override
    @Transactional(readOnly = true)
    public Favorite get(long id) {
        FavoriteEntity favoriteEntity = favoriteRepository.findById(id).orElseThrow(Common404Exception::new);

        return favoriteMapper.favoriteEntityToDomain(favoriteEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Favorite> list() {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Favorite> list(long userId) {
        favoriteRepository.findAllByUserId(userId).stream().map(
                favoriteMapper::favoriteEntityToDomain
        ).toList();
        return favoriteRepository.findAllByUserId(userId).stream().map(
                favoriteMapper::favoriteEntityToDomain
        ).toList();
    }
}
