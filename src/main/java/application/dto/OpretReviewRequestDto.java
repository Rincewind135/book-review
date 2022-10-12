package application.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OpretReviewRequestDto {
    private String bogId;
    private int score;
    private String reviewForfatter;
    private String beskrivelse;
}
