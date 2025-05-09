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

    // 캐시 저장 메서드
    public void saveToCache(String key, Object value) {
        try {
            getValueOperations().set(key, value);
        } catch (Exception e) {
            log.error("캐싱 오류: {}", e.getMessage());
        }
    }

    // 캐시 삭제 메서드
    public void deleteFromCache(String key) {
        try {
            redisConfig.redisTemplate().delete(key);
        } catch (Exception e) {
            log.error("캐시 삭제 오류: {}", e.getMessage());
        }
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
