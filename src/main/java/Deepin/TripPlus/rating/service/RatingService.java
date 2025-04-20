package Deepin.TripPlus.rating.service;

import Deepin.TripPlus.entity.Rating;
import Deepin.TripPlus.rating.ratingDto.RatingDto;
import jakarta.servlet.http.HttpServletRequest;

public interface RatingService {

    Rating ratingProcess(HttpServletRequest request, RatingDto ratingDto);

}
