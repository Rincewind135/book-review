package application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OpretBogResponseDto extends ResponseDto {
    private StatusSubKode statusSubKode;

    private String bogId;

    @Builder
    public OpretBogResponseDto(StatusKode statusKode, String fejlBeskrivelse, StatusSubKode statusSubKode, String bogId) {
        super(statusKode, fejlBeskrivelse);
        this.statusSubKode = statusSubKode;
        this.bogId = bogId;
    }

    public enum StatusSubKode {
        BOG_FINDES_ALLEREDE
    }
}
