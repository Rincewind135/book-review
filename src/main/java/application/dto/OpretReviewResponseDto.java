package application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OpretReviewResponseDto extends ResponseDto {
    private StatusSubKode statusSubKode;

    private String reviewId;

    @Builder
    public OpretReviewResponseDto(StatusKode statusKode, String fejlBeskrivelse, String transaktionsId, StatusSubKode statusSubKode, String reviewId) {
        super(statusKode, fejlBeskrivelse, transaktionsId);
        this.statusSubKode = statusSubKode;
        this.reviewId = reviewId;
    }

    public enum StatusSubKode {
        UKENDT_BOG
        ,UGYLDIG_SCORE
        ,EXCEPTION_THROWN
    }
}
