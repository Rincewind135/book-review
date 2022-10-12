package application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HentBogResponseDto extends ResponseDto {
    private StatusSubKode statusSubKode;

    private String bogId;
    private String titel;
    private String forfatter;
    private String blurb;

    @Builder
    public HentBogResponseDto(StatusKode statusKode, String fejlBeskrivelse, StatusSubKode statusSubKode, String bogId, String titel, String forfatter, String blurb) {
        super(statusKode, fejlBeskrivelse);
        this.statusSubKode = statusSubKode;
        this.bogId = bogId;
        this.titel = titel;
        this.forfatter = forfatter;
        this.blurb = blurb;
    }

    public enum StatusSubKode {
        UKENDT_BOG
    }
}
