package application.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OpretReviewRequestDto extends RequestDto {
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

    @Builder
    public OpretReviewRequestDto(String transaktionsId, String bogId, int score, String reviewForfatter, String beskrivelse) {
        super(transaktionsId);
        this.bogId = bogId;
        this.score = score;
        this.reviewForfatter = reviewForfatter;
        this.beskrivelse = beskrivelse;
    }
}
