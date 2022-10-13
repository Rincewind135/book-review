package application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OpretBogRequestDto extends RequestDto {
    @NotBlank
    @Size(max = 255)
    private String titel;

    @NotBlank
    @Size(max = 255)
    private String forfatter;

    @NotBlank
    @Size(max = 255)
    private String blurb;

    @Builder
    public OpretBogRequestDto(String transaktionsId, String titel, String forfatter, String blurb) {
        super(transaktionsId);
        this.titel = titel;
        this.forfatter = forfatter;
        this.blurb = blurb;
    }
}
