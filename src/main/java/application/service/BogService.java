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
public class BogService {

    private final BogRepository bogRepository;

    public Optional<Bog> findBogByTitel(String titel) {
        return bogRepository.findBogByTitel(titel);
    }

    public Optional<Bog> findBogById(String id) {
        return bogRepository.findBogById(id);
    }

    public OpretBogResponseDto opret(OpretBogRequestDto requestDto) {

        Optional<OpretBogResponseDto> fejlFundet = validerInput(requestDto);

        if (fejlFundet.isPresent()) {
            return fejlFundet.get();
        }


        Bog bog = opretBog(requestDto);

        return OpretBogResponseDto.builder()
                .bogId(bog.getId())
                .statusKode(ResponseDto.StatusKode.OK)
                .build();
    }

    private Optional<OpretBogResponseDto> validerInput(OpretBogRequestDto requestDto) {
        if (findBogByTitel(requestDto.getTitel()).isPresent()) {
            return fejlBogFindesAllerede(requestDto.getTitel());
        }

        return Optional.empty();
    }

    private Optional<OpretBogResponseDto> fejlBogFindesAllerede(String titel) {
        return Optional.of(
                OpretBogResponseDto.builder()
                        .statusKode(ResponseDto.StatusKode.FEJL)
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
