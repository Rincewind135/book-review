package application.dto;

import lombok.*;

@Getter
@Setter
public class OpretBogRequestDto extends RequestDto {
    private String titel;
    private String forfatter;
    private String blurb;

    @Builder
    public OpretBogRequestDto(String transaktionsId, String titel, String forfatter, String blurb) {
        super(transaktionsId);
        this.titel = titel;
        this.forfatter = forfatter;
        this.blurb = blurb;
    }
}
