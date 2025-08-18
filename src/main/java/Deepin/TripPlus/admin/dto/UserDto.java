package Deepin.TripPlus.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;

    private String userName;

    private String email;

    private String gender;

    private String birth;

    private Boolean isSuspended;

    private String createdDate;

}
