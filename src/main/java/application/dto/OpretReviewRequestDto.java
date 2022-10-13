package application.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OpretReviewRequestDto {
    @NotBlank
    @Size(min = 36, max = 36)
    private String bogId;

    @Min(0)
    @Max(5)
    private int score;

    @NotBlank
    @Size(max = 255)
    private String reviewForfatter;

    @NotBlank
    @Size(max = 255)
    private String beskrivelse;
}
