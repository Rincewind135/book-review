package application.service;

import application.dto.*;
import application.entity.Bog;
import application.repository.BogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BogWriterService {

    private final BogRepository bogRepository;
    private final BogReaderService bogReaderService;

    public OpretBogResponseDto opret(OpretBogRequestDto requestDto) {

        Optional<OpretBogResponseDto> fejlFundet = validerInput(requestDto);

        if (fejlFundet.isPresent()) {
            return fejlFundet.get();
        }

        Bog bog = opretBog(requestDto);
        return bogErOprettetOkay(bog);
    }

    private static OpretBogResponseDto bogErOprettetOkay(Bog bog) {
        return OpretBogResponseDto.builder()
                .bogId(bog.getId())
                .statusKode(ResponseDto.StatusKode.OK)
                .build();
    }

    private Optional<OpretBogResponseDto> validerInput(OpretBogRequestDto requestDto) {
        if (bogReaderService.findBogByTitel(requestDto.getTitel()).isPresent()) {
            return fejlBogFindesAllerede(requestDto.getTitel());
        }

        return Optional.empty();
    }

    private Optional<OpretBogResponseDto> fejlBogFindesAllerede(String titel) {
        return Optional.of(
                OpretBogResponseDto.builder()
                        .statusKode(ResponseDto.StatusKode.INPUT_FEJL)
                        .statusSubKode(OpretBogResponseDto.StatusSubKode.BOG_FINDES_ALLEREDE)
                        .fejlBeskrivelse("Bogen med titel " + titel + " er allerede oprettet")
                        .build()
        );
    }

    private Bog opretBog(OpretBogRequestDto requestDto) {
        return bogRepository.save(Bog.builder()
                .id(UUID. randomUUID().toString())
                .forfatter(requestDto.getForfatter())
                .titel(requestDto.getTitel())
                .blurb(requestDto.getBlurb())
                .build());
    }
}
