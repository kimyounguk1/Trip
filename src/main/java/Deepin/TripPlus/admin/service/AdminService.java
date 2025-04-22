package Deepin.TripPlus.admin.service;

import Deepin.TripPlus.entity.CourseDetail;
import Deepin.TripPlus.entity.Inquire;
import Deepin.TripPlus.entity.User;

import java.util.List;

public interface AdminService {

    List<User> UsersProcess();

    List<CourseDetail> CourseDetailsProcess();

    List<Inquire> InquiresProcess();

}
