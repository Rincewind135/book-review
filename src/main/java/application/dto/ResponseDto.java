package application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private StatusKode statusKode;
    private String fejlBeskrivelse;
    private String transaktionsId;

    public enum StatusKode {
        OK
        ,INPUT_FEJL
        ,TEKNISK_FEJL
    }
}
