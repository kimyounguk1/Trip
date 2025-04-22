package Deepin.TripPlus.admin.service;

import Deepin.TripPlus.entity.CourseDetail;
import Deepin.TripPlus.entity.Inquire;
import Deepin.TripPlus.entity.User;
import Deepin.TripPlus.repository.SpringDataJpaCourseDtRepository;
import Deepin.TripPlus.repository.SpringDataJpaInquireRepository;
import Deepin.TripPlus.repository.SpringDataJpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final SpringDataJpaUserRepository userRepository;
    private final SpringDataJpaCourseDtRepository courseDtRepository;
    private final SpringDataJpaInquireRepository inquireRepository;

    @Override
    public List<User> UsersProcess() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public List<CourseDetail> CourseDetailsProcess() {
        List<CourseDetail> courseDts = courseDtRepository.findAll();
        return courseDts;
    }

    @Override
    public List<Inquire> InquiresProcess() {
        List<Inquire> inquires = inquireRepository.findAll();
        return inquires;
    }
}
