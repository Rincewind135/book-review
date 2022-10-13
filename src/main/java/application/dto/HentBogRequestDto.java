package application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class HentBogRequestDto extends RequestDto {
    @NotBlank
    @Size(max = 255)
    private String titel;

    @Builder
    public HentBogRequestDto(String transaktionsId, String titel) {
        super(transaktionsId);
        this.titel = titel;
    }
}
