package application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HentBogResponseDto extends ResponseDto {
    private StatusSubKode statusSubKode;

    private String bogId;
    private String titel;
    private String forfatter;
    private String blurb;

    public HentBogResponseDto() {
        super();
    }

    public enum StatusSubKode {
        UKENDT_BOG,
        EXCEPTION_THROWN
    }
}
