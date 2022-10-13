package application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private StatusKode statusKode;
    private String fejlBeskrivelse;
    private String transaktionsId;

    public ResponseDto() {
    }

    public enum StatusKode {
        OK
        ,INPUT_FEJL
        ,TEKNISK_FEJL
    }
}
