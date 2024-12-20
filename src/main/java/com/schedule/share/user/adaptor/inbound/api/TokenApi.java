package com.schedule.share.user.adaptor.inbound.api;

import com.schedule.share.common.model.ResponseModel;
import com.schedule.share.user.adaptor.inbound.api.dto.ReissueAccessTokenRequestDTO;
import com.schedule.share.user.adaptor.inbound.api.dto.TokenResponseDTO;
import com.schedule.share.user.adaptor.inbound.api.mapper.TokenDTOMapper;
import com.schedule.share.user.application.port.inbound.TokenCommand;
import com.schedule.share.user.application.port.inbound.TokenQuery;
import com.schedule.share.user.application.port.inbound.TokenServiceUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인", description = "로그인 API")
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenApi {

    private static final Logger log = LoggerFactory.getLogger(TokenApi.class);
    private final TokenCommand tokenCommand;
    private final TokenQuery tokenQuery;
    private final TokenServiceUseCase tokenServiceUseCase;

    private final TokenDTOMapper tokenDTOMapper;

    @PostMapping("/access-token")
    public ResponseModel<TokenResponseDTO.AccessAndRefreshToken> reissueAccessToken(@RequestBody ReissueAccessTokenRequestDTO.RefreshToken refreshToken) {
        log.debug("[refresh] : {}", refreshToken);
        TokenResponseDTO.AccessAndRefreshToken responseDTO = tokenDTOMapper.toResponseDTO(
                tokenServiceUseCase.reissueAccessToken(refreshToken.refreshToken())
        );
        log.debug("responseDTO : {} ", responseDTO);
        return ResponseModel.of(responseDTO);
    }
}
