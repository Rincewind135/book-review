package application.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class HentReviewRequestDto {
    private String reviewId;
}
