package application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HentReviewResponseDto extends ResponseDto {
    private StatusSubKode statusSubKode;

    private String reviewId;
    private String bogId;
    private int score;
    private String reviewForfatter;
    private String beskrivelse;

    public HentReviewResponseDto() {
    }

    public enum StatusSubKode {
        UKENDT_REVIEW
        ,EXCEPTION_THROWN
    }
}
