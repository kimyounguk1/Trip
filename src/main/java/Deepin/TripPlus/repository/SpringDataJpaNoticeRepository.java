package Deepin.TripPlus.repository;

import Deepin.TripPlus.entity.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface SpringDataJpaNoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findById(Long id);

    List<Notice> findByTitleContaining(String title);

}
