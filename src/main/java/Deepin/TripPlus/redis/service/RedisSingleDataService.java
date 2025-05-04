package Deepin.TripPlus.redis.service;


import java.time.Duration;

public interface RedisSingleDataService {

    int setSingleData(String key, Object value);

    int setSingleData(String key, Object value, Duration duration);
}
