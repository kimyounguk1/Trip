package Deepin.TripPlus.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class KakaoUser {
    private String email;
    private String name;
    private String role;
}
