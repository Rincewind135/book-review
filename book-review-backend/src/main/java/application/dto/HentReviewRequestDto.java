package application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class HentReviewRequestDto {
    @NotBlank
    @Size(min = 36, max = 36)
    private String reviewId;

    @NotBlank
    @Size(min = 36, max = 36)
    private String transaktionsId;

    @Builder
    public HentReviewRequestDto(String transaktionsId, String reviewId) {
        this.transaktionsId = transaktionsId;
        this.reviewId = reviewId;
    }
}
