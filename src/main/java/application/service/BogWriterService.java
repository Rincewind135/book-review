package application.service;

import application.dto.OpretBogRequestDto;
import application.dto.OpretBogResponseDto;
import application.dto.ResponseDto;
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
        try {
            return opretHvisValid(requestDto);
        } catch (Exception e) {
            return tekniskFejl(requestDto, e);
        }
    }

    private OpretBogResponseDto opretHvisValid(OpretBogRequestDto requestDto) {
        Optional<OpretBogResponseDto> fejlFundet = validerInput(requestDto);
        if (fejlFundet.isPresent()) {
            return fejlFundet.get();
        }
        Bog bog = opretBog(requestDto);
        return bogErOprettetOkay(requestDto, bog);
    }

    private static OpretBogResponseDto bogErOprettetOkay(OpretBogRequestDto requestDto, Bog bog) {
        return OpretBogResponseDto.builder()
                .bogId(bog.getId())
                .statusKode(ResponseDto.StatusKode.OK)
                .transaktionsId(requestDto.getTransaktionsId())
                .build();
    }

    private Optional<OpretBogResponseDto> validerInput(OpretBogRequestDto requestDto) {
        if (bogReaderService.findBogByTitel(requestDto.getTitel()).isPresent()) {
            return fejlBogFindesAllerede(requestDto);
        }

        return Optional.empty();
    }

    private Optional<OpretBogResponseDto> fejlBogFindesAllerede(OpretBogRequestDto requestDto) {
        return Optional.of(
                OpretBogResponseDto.builder()
                        .statusKode(ResponseDto.StatusKode.INPUT_FEJL)
                        .statusSubKode(OpretBogResponseDto.StatusSubKode.BOG_FINDES_ALLEREDE)
                        .fejlBeskrivelse("Bogen med titel " + requestDto.getTitel() + " er allerede oprettet")
                        .transaktionsId(requestDto.getTransaktionsId())
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

    private OpretBogResponseDto tekniskFejl(OpretBogRequestDto requestDto, Exception e) {
        return OpretBogResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.TEKNISK_FEJL)
                .statusSubKode(OpretBogResponseDto.StatusSubKode.EXCEPTION_THROWN)
                .fejlBeskrivelse(e.getMessage())
                .transaktionsId(requestDto.getTransaktionsId())
                .build();

    }
}
