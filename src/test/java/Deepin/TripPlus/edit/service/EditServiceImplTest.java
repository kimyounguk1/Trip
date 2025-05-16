package Deepin.TripPlus.edit.service;

import Deepin.TripPlus.entity.Notice;
import Deepin.TripPlus.repository.SpringDataJpaNoticeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class EditServiceImplTest {

    @Autowired
    EditService editService;
    @Autowired
    SpringDataJpaNoticeRepository springDataJpaNoticeRepository;

    @Test
    void save(){
        Notice notice = new Notice();
        notice.setTitle("title");
        notice.setContent("content");
        springDataJpaNoticeRepository.save(notice);
    }

}