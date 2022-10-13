package application.dto;

import lombok.*;

@Getter
@Setter
public class HentBogRequestDto extends RequestDto {
    private String titel;

    @Builder
    public HentBogRequestDto(String transaktionsId, String titel) {
        super(transaktionsId);
        this.titel = titel;
    }
}
