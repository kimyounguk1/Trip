package Deepin.TripPlus.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class KakaoUser {
    private String email;
    private String name;
}
