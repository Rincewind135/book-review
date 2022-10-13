package application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OpretBogRequestDto {
    @NotBlank
    @Size(max = 255)
    private String titel;

    @NotBlank
    @Size(max = 255)
    private String forfatter;

    @NotBlank
    @Size(max = 255)
    private String blurb;

    @NotBlank
    @Size(min = 36, max = 36)
    private String transaktionsId;

    @Builder
    public OpretBogRequestDto(String transaktionsId, String titel, String forfatter, String blurb) {
        this.transaktionsId = transaktionsId;
        this.titel = titel;
        this.forfatter = forfatter;
        this.blurb = blurb;
    }
}
