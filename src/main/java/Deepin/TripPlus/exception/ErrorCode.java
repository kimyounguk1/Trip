package Deepin.TripPlus.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICATE_EMAIL(405, "E001", "이미 사용 중인 이메일입니다."),
    EXPIRED_TOKEN(406, "E002", "토큰이 만료되었습니다."),
    NON_EXIST_USER(407, "E003", "존재하지 않는 유저입니다."),
    //SUSPENDED_ACCOUNT(408, "E004", "정지된 계정입니다.");
    NON_EXIST_NOTICE(409, "E005", "존재하지 않는 공지입니다."),
    NON_EXIST_COURSE(410, "E006", "존재하지 않는 코스입니다."),
    NON_EXIST_INQUIRE(411, "E007", "존재하지 않는 문의사항입니다");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
