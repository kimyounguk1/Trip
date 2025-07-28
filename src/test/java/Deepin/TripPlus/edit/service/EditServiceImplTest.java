package Deepin.TripPlus.edit.service;

import Deepin.TripPlus.entity.Notice;
import Deepin.TripPlus.repository.SpringDataJpaNoticeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
        List<Notice> notices = springDataJpaNoticeRepository.findAll();
        for(Notice notice1 : notices) {
            System.out.println(notice1.getTitle());
        }
    }

}