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
        HentBogResponseDto responseDto = new HentBogResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.OK);
        responseDto.setTitel(bog.getTitel());
        responseDto.setForfatter(bog.getForfatter());
        responseDto.setBlurb(bog.getBlurb());
        responseDto.setBogId(bog.getId());
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
                return responseDto;
    }

    private static HentBogResponseDto fejlUkendtBog(HentBogRequestDto requestDto) {
        HentBogResponseDto responseDto = new HentBogResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.INPUT_FEJL);
        responseDto.setStatusSubKode(HentBogResponseDto.StatusSubKode.UKENDT_BOG);
        responseDto.setFejlBeskrivelse("Kunne ikke finde en bog med titel " + requestDto.getTitel());
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }

    public Optional<Bog> findBogByTitel(String titel) {
        return bogRepository.findBogByTitel(titel);
    }

    public Optional<Bog> findBogById(String id) {
        return bogRepository.findBogById(id);
    }

    private HentBogResponseDto tekniskFejl(HentBogRequestDto requestDto, Exception e) {
        HentBogResponseDto responseDto = new HentBogResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.TEKNISK_FEJL);
        responseDto.setStatusSubKode(HentBogResponseDto.StatusSubKode.EXCEPTION_THROWN);
        responseDto.setFejlBeskrivelse(e.getMessage());
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }
}
