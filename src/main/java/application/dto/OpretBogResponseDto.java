package application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpretBogResponseDto extends ResponseDto {
    private StatusSubKode statusSubKode;

    private String bogId;

    public OpretBogResponseDto() {
        super();
    }

    public enum StatusSubKode {
        BOG_FINDES_ALLEREDE,
        EXCEPTION_THROWN
    }
}
