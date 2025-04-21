package Deepin.TripPlus.rating.controller;

import Deepin.TripPlus.commonDto.ApiResponse;
import Deepin.TripPlus.rating.ratingDto.RatingDto;
import Deepin.TripPlus.rating.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/rating")
    public ResponseEntity<ApiResponse<?>> ratingProcess(HttpServletRequest request, @ModelAttribute RatingDto ratingDto) {

        ratingService.ratingProcess(request, ratingDto);

        return ResponseEntity.ok(ApiResponse.success(null));

    }


}
