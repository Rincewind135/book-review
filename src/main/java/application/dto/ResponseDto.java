package application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
abstract public class ResponseDto {
    private StatusKode statusKode;
    private String fejlBeskrivelse;

    public enum StatusKode {
        OK
        ,FEJL
    }
}
