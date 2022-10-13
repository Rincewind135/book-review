package application.service;

import application.dto.HentBogRequestDto;
import application.dto.HentBogResponseDto;
import application.dto.ResponseDto;
import application.entity.Bog;
import application.repository.BogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BogReaderService {

    private final BogRepository bogRepository;

    public HentBogResponseDto hent(HentBogRequestDto requestDto) {
        Optional<Bog> bogOptional = findBogByTitel(requestDto.getTitel());

        if (bogOptional.isPresent()) {
            Bog bog = bogOptional.get();
            return bogErHentet(bog);
        } else {
            return fejlUkendtBog(requestDto);
        }

    }

    private static HentBogResponseDto bogErHentet(Bog bog) {
        return HentBogResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.OK)
                .titel(bog.getTitel())
                .forfatter(bog.getForfatter())
                .blurb(bog.getBlurb())
                .bogId(bog.getId())
                .build();
    }

    private static HentBogResponseDto fejlUkendtBog(HentBogRequestDto requestDto) {
        return HentBogResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.INPUT_FEJL)
                .statusSubKode(HentBogResponseDto.StatusSubKode.UKENDT_BOG)
                .fejlBeskrivelse("Kunne ikke finde en bog med titel " + requestDto.getTitel())
                .build();
    }

    public Optional<Bog> findBogByTitel(String titel) {
        return bogRepository.findBogByTitel(titel);
    }

    public Optional<Bog> findBogById(String id) {
        return bogRepository.findBogById(id);
    }
}
