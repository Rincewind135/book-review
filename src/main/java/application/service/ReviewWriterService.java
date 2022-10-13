package application.service;

import application.dto.OpretReviewRequestDto;
import application.dto.OpretReviewResponseDto;
import application.dto.ResponseDto;
import application.entity.Review;
import application.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewWriterService {

    private final BogReaderService bogReaderService;
    private final ReviewRepository reviewRepository;

    public OpretReviewResponseDto opret(OpretReviewRequestDto requestDto) {
        try {
            return opretHvisValid(requestDto);
        } catch (Exception e) {
            return tekniskFejl(requestDto, e);
        }
    }

    private OpretReviewResponseDto opretHvisValid(OpretReviewRequestDto requestDto) {
        Optional<OpretReviewResponseDto> fejlFundet = validerInput(requestDto);
        if (fejlFundet.isPresent()) {
            return fejlFundet.get();
        }
        Review review = opretReview(requestDto);
        return reviewOprettetOkay(requestDto, review);
    }

    private Optional<OpretReviewResponseDto> validerInput(OpretReviewRequestDto requestDto) {
        if (requestDto.getScore() < 0) {
            return Optional.of(fejlUgyldigScore(requestDto, "Score må mindst være 0"));
        }
        if (requestDto.getScore() > 5) {
            return Optional.of(fejlUgyldigScore(requestDto, "Score må højest være 0"));
        }
        if (!bogReaderService.findBogById(requestDto.getBogId()).isPresent()) {
            return Optional.of(fejlUkendtBog(requestDto));
        }

        return Optional.empty();
    }

    private OpretReviewResponseDto fejlUgyldigScore(OpretReviewRequestDto requestDto, String fejlBeskrivelse) {
        OpretReviewResponseDto responseDto = new OpretReviewResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.INPUT_FEJL);
        responseDto.setStatusSubKode(OpretReviewResponseDto.StatusSubKode.UGYLDIG_SCORE);
        responseDto.setFejlBeskrivelse(fejlBeskrivelse);
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;

    }

    private OpretReviewResponseDto fejlUkendtBog(OpretReviewRequestDto requestDto) {
        OpretReviewResponseDto responseDto = new OpretReviewResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.INPUT_FEJL);
        responseDto.setStatusSubKode(OpretReviewResponseDto.StatusSubKode.UKENDT_BOG);
        responseDto.setFejlBeskrivelse("Kunne ikke finde en bog med ID " + requestDto.getBogId());
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }

    private Review opretReview(OpretReviewRequestDto requestDto) {
        return reviewRepository.save(Review.builder()
                .id(UUID.randomUUID().toString())
                .reviewForfatter(requestDto.getReviewForfatter())
                .score(requestDto.getScore())
                .beskrivelse(requestDto.getBeskrivelse())
                .bog(bogReaderService.findBogById(requestDto.getBogId()).orElse(null))
                .build());
    }

    private static OpretReviewResponseDto reviewOprettetOkay(OpretReviewRequestDto requestDto, Review review) {
        OpretReviewResponseDto responseDto = new OpretReviewResponseDto();
        responseDto.setReviewId(review.getId());
        responseDto.setStatusKode(ResponseDto.StatusKode.OK);
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }

    private OpretReviewResponseDto tekniskFejl(OpretReviewRequestDto requestDto, Exception e) {
        OpretReviewResponseDto responseDto = new OpretReviewResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.TEKNISK_FEJL);
        responseDto.setStatusSubKode(OpretReviewResponseDto.StatusSubKode.EXCEPTION_THROWN);
        responseDto.setFejlBeskrivelse(e.getMessage());
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;

    }
}
