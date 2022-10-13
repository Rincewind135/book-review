package application.service;

import application.dto.HentReviewRequestDto;
import application.dto.HentReviewResponseDto;
import application.dto.ResponseDto;
import application.entity.Review;
import application.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewReaderService {

    private final ReviewRepository reviewRepository;

    public HentReviewResponseDto hent(HentReviewRequestDto requestDto) {
        try {
            return hentHvisEksisterer(requestDto);
        } catch (Exception e) {
            return tekniskFejl(requestDto, e);
        }
    }

    private HentReviewResponseDto hentHvisEksisterer(HentReviewRequestDto requestDto) {
        Optional<Review> reviewOptional = indlaes(requestDto);

        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            return mapReviewTilDto(requestDto, review);
        } else {
            return fejlUkendtReview(requestDto);
        }
    }

    private Optional<Review> indlaes(HentReviewRequestDto requestDto) {
        return reviewRepository.findById(requestDto.getReviewId());
    }

    private HentReviewResponseDto mapReviewTilDto(HentReviewRequestDto requestDto, Review review) {
        return HentReviewResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.OK)
                .reviewForfatter(review.getReviewForfatter())
                .beskrivelse(review.getBeskrivelse())
                .score(review.getScore())
                .reviewId(review.getId())
                .bogId(review.getBog().getId())
                .transaktionsId(requestDto.getTransaktionsId())
                .build();
    }

    private HentReviewResponseDto fejlUkendtReview(HentReviewRequestDto requestDto) {
        return HentReviewResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.INPUT_FEJL)
                .statusSubKode(HentReviewResponseDto.StatusSubKode.UKENDT_REVIEW)
                .fejlBeskrivelse("Kunne ikke finde en review med ID " + requestDto.getReviewId())
                .transaktionsId(requestDto.getTransaktionsId())
                .build();
    }

    private HentReviewResponseDto tekniskFejl(HentReviewRequestDto requestDto, Exception e) {
        return HentReviewResponseDto.builder()
                .statusKode(ResponseDto.StatusKode.TEKNISK_FEJL)
                .statusSubKode(HentReviewResponseDto.StatusSubKode.EXCEPTION_THROWN)
                .fejlBeskrivelse(e.getMessage())
                .transaktionsId(requestDto.getTransaktionsId())
                .build();
    }
}
