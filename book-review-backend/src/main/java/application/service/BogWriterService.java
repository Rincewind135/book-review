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
        OpretBogResponseDto responseDto = new OpretBogResponseDto();
        responseDto.setBogId(bog.getId());
        responseDto.setStatusKode(ResponseDto.StatusKode.OK);
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }

    private Optional<OpretBogResponseDto> validerInput(OpretBogRequestDto requestDto) {
        if (bogReaderService.findBogByTitel(requestDto.getTitel()).isPresent()) {
            return fejlBogFindesAllerede(requestDto);
        }

        return Optional.empty();
    }

    private Optional<OpretBogResponseDto> fejlBogFindesAllerede(OpretBogRequestDto requestDto) {
        OpretBogResponseDto responseDto = new OpretBogResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.INPUT_FEJL);
        responseDto.setStatusSubKode(OpretBogResponseDto.StatusSubKode.BOG_FINDES_ALLEREDE);
        responseDto.setFejlBeskrivelse("Bogen med titel " + requestDto.getTitel() + " er allerede oprettet");
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return Optional.of(responseDto);
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
        OpretBogResponseDto responseDto = new OpretBogResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.TEKNISK_FEJL);
        responseDto.setStatusSubKode(OpretBogResponseDto.StatusSubKode.EXCEPTION_THROWN);
        responseDto.setFejlBeskrivelse("Bogen med titel " + requestDto.getTitel() + " er allerede oprettet");
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }
}
