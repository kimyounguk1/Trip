package Deepin.TripPlus.auth.service;

import Deepin.TripPlus.auth.dto.CourseDto;
import Deepin.TripPlus.auth.dto.HomeDto;
import Deepin.TripPlus.auth.dto.OnboardingDto;
import Deepin.TripPlus.auth.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AuthService {

    void registerProcess(RegisterDto registerDto);

    void onboardingProcess(OnboardingDto onboardingDto, HttpServletRequest request);

    HomeDto homeProcess(HttpServletRequest request);

}
