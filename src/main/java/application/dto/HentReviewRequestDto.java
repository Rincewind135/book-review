package application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class HentReviewRequestDto extends RequestDto {
    @NotBlank
    @Size(min = 36, max = 36)
    private String reviewId;

    @Builder
    public HentReviewRequestDto(String transaktionsId, String reviewId) {
        super(transaktionsId);
        this.reviewId = reviewId;
    }
}
