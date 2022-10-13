package application.dto;

import lombok.*;

@Getter
@Setter
public class HentReviewRequestDto extends RequestDto {
    private String reviewId;

    @Builder
    public HentReviewRequestDto(String transaktionsId, String reviewId) {
        super(transaktionsId);
        this.reviewId = reviewId;
    }
}
