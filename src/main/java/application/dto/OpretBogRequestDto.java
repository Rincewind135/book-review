package application.dto;

import lombok.*;

@Getter
@Setter
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class OpretBogRequestDto {
    private String titel;
    private String forfatter;
    private String blurb;
}
