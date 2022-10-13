package application.dto;

import lombok.Builder;
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

    @Builder
    public HentReviewResponseDto(StatusKode statusKode, String fejlBeskrivelse, String transaktionsId, StatusSubKode statusSubKode, String reviewId, String bogId, int score, String reviewForfatter, String beskrivelse) {
        super(statusKode, fejlBeskrivelse, transaktionsId);
        this.statusSubKode = statusSubKode;
        this.reviewId = reviewId;
        this.bogId = bogId;
        this.score = score;
        this.reviewForfatter = reviewForfatter;
        this.beskrivelse = beskrivelse;
    }

    public enum StatusSubKode {
        UKENDT_REVIEW
        ,EXCEPTION_THROWN
    }
}
