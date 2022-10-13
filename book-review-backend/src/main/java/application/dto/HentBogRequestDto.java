package application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class HentBogRequestDto {
    @NotBlank
    @Size(max = 255)
    private String titel;

    @NotBlank
    @Size(min = 36, max = 36)
    private String transaktionsId;

    @Builder
    public HentBogRequestDto(String transaktionsId, String titel) {
        this.transaktionsId = transaktionsId;
        this.titel = titel;
    }
}
