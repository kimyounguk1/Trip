//package Deepin.TripPlus.redis.service;
//
//import Deepin.TripPlus.redis.RedisConfig;
//import Deepin.TripPlus.redis.RedisHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//
//@Service
//@RequiredArgsConstructor
//public class RedisSingleDataServiceImpl implements RedisSingleDataService {
//
//    private final RedisHandler redisHandler;
//    private final RedisConfig redisConfig;
//
//    @Override
//    public int setSingleData(String key, Object value) {
//        return redisHandler.executeOperation(()->redisHandler.getValueOperations().set(key,value));
//    }
//
//    @Override
//    public int setSingleData(String key, Object value, Duration duration) {
//        return redisHandler.executeOperation(()->redisHandler.getValueOperations().set(key,value,duration));
//    }
//
//    @Override
//    public Object getSingleData(String key) {
//
//        if(redisHandler.getValueOperations().get(key) == null){
//            return "";
//        }
//        return redisHandler.getValueOperations().get(key);
//    }
//
//    @Override
//    public int deleteSingleData(String key) {
//        return redisHandler.executeOperation(()->redisConfig.redisTemplate().delete(key));
//    }
//}
