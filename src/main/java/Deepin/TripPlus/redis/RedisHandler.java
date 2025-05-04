package Deepin.TripPlus.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisHandler {

    private final RedisConfig redisConfig;

    public ListOperations<String, Object> getListOperations(){
        return redisConfig.redisTemplate().opsForList();
    }

    public ValueOperations<String, Object> getValueOperations(){
        return redisConfig.redisTemplate().opsForValue();
    }

    public int executeOperation(Runnable operation){
        try{
            operation.run();
            return 1;
        }catch (Exception e){
            log.info("Redis 작업 오류 발생 :: {}", e.getMessage());
            return 0;
        }
    }
}
