package Deepin.TripPlus.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    DUPLICATE_EMAIL(605, "E001", "이미 사용 중인 이메일입니다."),
    EXPIRED_TOKEN(606, "E002", "토큰이 만료되었습니다."),
    NON_EXIST_USER(607, "E003", "존재하지 않는 유저입니다."),
    //SUSPENDED_ACCOUNT(608, "E004", "정지된 계정입니다.");
    NON_EXIST_NOTICE(609, "E005", "존재하지 않는 공지입니다."),
    NON_EXIST_COURSE(610, "E006", "존재하지 않는 코스입니다."),
    NON_EXIST_INQUIRE(611, "E007", "존재하지 않는 문의사항입니다"),
    MODEL_TRAIN_FAIL(612, "E008", "모델 학습에 실패하였습니다."),
    NON_EXIST_MODEL(613, "E009", "존재하지 않는 모델입니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


}
