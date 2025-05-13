package Deepin.TripPlus.edit.service;

import Deepin.TripPlus.entity.Notice;
import Deepin.TripPlus.repository.SpringDataJpaNoticeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EditServiceImplTest {

    @Autowired
    private EditService editService;
    @Autowired
    private SpringDataJpaNoticeRepository noticeRepository;

    @Test
    void noticeProcess() {
        Notice notice = new Notice();
        notice.setTitle("title");
        notice.setContent("content");

        noticeRepository.save(notice);
        Notice notice1 = noticeRepository.findById(notice.getId()).get();

        assertEquals(notice.getTitle(), notice1.getTitle());

    }
}