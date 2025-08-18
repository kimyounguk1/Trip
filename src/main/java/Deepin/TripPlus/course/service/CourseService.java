package Deepin.TripPlus.course.service;

import Deepin.TripPlus.auth.dto.CourseDto;
import Deepin.TripPlus.course.dto.ContentDto;
import Deepin.TripPlus.course.dto.ContentInput;
import Deepin.TripPlus.entity.Course;
import Deepin.TripPlus.repository.SpringDataJpaCourseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final SpringDataJpaCourseRepository courseRepository;

    public List<CourseDto> getCoursesByUserId(Long userId) {
        List<Course> courseList = courseRepository.findByUsers_Id(userId);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return courseList.stream()
                .map(course -> new CourseDto(
                        course.getId(),
                        course.getTitle(),
                        course.getArea(),
                        course.getTripType(),
                        formatter.format(course.getStartDate()),
                        formatter.format(course.getEndDate())
                ))
                .collect(Collectors.toList());
    }


    // Windows 절대 경로 (백슬래시 대신 슬래시 사용 권장)
    private static final String PROJECT_ROOT = "C:/spring/TripPlus";

    public List<ContentDto> getRecommendations(ContentInput input) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInput = mapper.writeValueAsString(input);

        // 파이썬 스크립트 상대경로 (PROJECT_ROOT 기준)
        String pythonScriptPath = "src/main/ai/content_based/predict.py";

        ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath, "-");
        pb.directory(new File(PROJECT_ROOT));  // 작업 디렉토리 지정

        Process process = pb.start();

        // JSON 입력 전달
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(jsonInput);
            writer.flush();
        }

        // 표준 출력 읽기
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        }

        // 에러 출력 로그 (디버깅용)
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println("PYTHON ERR > " + errorLine);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Python process failed with exit code: " + exitCode);
        }

        // JSON 결과 파싱
        List<ContentDto> recommendations = mapper.readValue(
                output.toString(),
                new TypeReference<List<ContentDto>>() {}
        );

        return recommendations;
    }

}
