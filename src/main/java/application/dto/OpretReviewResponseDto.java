package application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpretReviewResponseDto extends ResponseDto {
    private StatusSubKode statusSubKode;

    private String reviewId;

    public OpretReviewResponseDto() {
    }

    public enum StatusSubKode {
        UKENDT_BOG
        ,UGYLDIG_SCORE
        ,EXCEPTION_THROWN
    }
}
