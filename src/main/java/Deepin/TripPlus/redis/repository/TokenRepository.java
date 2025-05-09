package Deepin.TripPlus.redis.repository;

import Deepin.TripPlus.redis.entity.RefreshEntity;
import org.springframework.data.repository.CrudRepository;


public interface TokenRepository extends CrudRepository<RefreshEntity, Long> {

    RefreshEntity findByUsername(String username);

    Boolean existsByRefresh(String refresh);

    void deleteByRefresh(String refresh);
}
