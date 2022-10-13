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
        try {
            return hentHvisEksisterer(requestDto);
        } catch (Exception e) {
            return tekniskFejl(requestDto, e);
        }
    }

    private HentBogResponseDto hentHvisEksisterer(HentBogRequestDto requestDto) {
        Optional<Bog> bogOptional = findBogByTitel(requestDto.getTitel());

        if (bogOptional.isPresent()) {
            Bog bog = bogOptional.get();
            return mapBogTilDto(requestDto, bog);
        } else {
            return fejlUkendtBog(requestDto);
        }

    }

    private static HentBogResponseDto mapBogTilDto(HentBogRequestDto requestDto, Bog bog) {
        return HentBogResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.OK)
                .titel(bog.getTitel())
                .forfatter(bog.getForfatter())
                .blurb(bog.getBlurb())
                .bogId(bog.getId())
                .transaktionsId(requestDto.getTransaktionsId())
                .build();
    }

    private static HentBogResponseDto fejlUkendtBog(HentBogRequestDto requestDto) {
        return HentBogResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.INPUT_FEJL)
                .statusSubKode(HentBogResponseDto.StatusSubKode.UKENDT_BOG)
                .fejlBeskrivelse("Kunne ikke finde en bog med titel " + requestDto.getTitel())
                .transaktionsId(requestDto.getTransaktionsId())
                .build();
    }

    public Optional<Bog> findBogByTitel(String titel) {
        return bogRepository.findBogByTitel(titel);
    }

    public Optional<Bog> findBogById(String id) {
        return bogRepository.findBogById(id);
    }

    private HentBogResponseDto tekniskFejl(HentBogRequestDto requestDto, Exception e) {
        return HentBogResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.TEKNISK_FEJL)
                .statusSubKode(HentBogResponseDto.StatusSubKode.EXCEPTION_THROWN)
                .fejlBeskrivelse(e.getMessage())
                .transaktionsId(requestDto.getTransaktionsId())
                .build();
    }
}
