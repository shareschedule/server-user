package com.schedule.share.infra.rdb.entity;

import com.schedule.share.user.domain.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(catalog = "user", name = "user")
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long recentCalendarId;

    private String nickname;

    private String method;

    private String ci;

    @Nullable
    private byte[] image;

    @CreatedDate
    private LocalDateTime registeredAt ;

    @CreatedDate
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void updateUserEntity(User user) {
        this.nickname = user.getNickname();
        this.image = user.getImage();

        if (user.getRecentCalendarId() != 0) {
            this.recentCalendarId = user.getRecentCalendarId();
        }
    }
}