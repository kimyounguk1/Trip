package Deepin.TripPlus.redis.entity;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter @Setter
@AllArgsConstructor
@RedisHash(value = "refresh")
public class RefreshEntity {

    @Id
    private String username;

    private String refresh;

    @TimeToLive
    private Long expiration;

}
