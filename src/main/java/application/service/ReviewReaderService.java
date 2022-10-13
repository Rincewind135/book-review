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
        HentReviewResponseDto responseDto = new HentReviewResponseDto();

        responseDto.setStatusKode(ResponseDto.StatusKode.OK);
        responseDto.setReviewForfatter(review.getReviewForfatter());
        responseDto.setBeskrivelse(review.getBeskrivelse());
        responseDto.setScore(review.getScore());
        responseDto.setReviewId(review.getId());
        responseDto.setBogId(review.getBog().getId());
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }

    private HentReviewResponseDto fejlUkendtReview(HentReviewRequestDto requestDto) {
        HentReviewResponseDto responseDto = new HentReviewResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.INPUT_FEJL);
        responseDto.setStatusSubKode(HentReviewResponseDto.StatusSubKode.UKENDT_REVIEW);
        responseDto.setFejlBeskrivelse("Kunne ikke finde en review med ID " + requestDto.getReviewId());
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }

    private HentReviewResponseDto tekniskFejl(HentReviewRequestDto requestDto, Exception e) {
        HentReviewResponseDto responseDto = new HentReviewResponseDto();
        responseDto.setStatusKode(ResponseDto.StatusKode.TEKNISK_FEJL);
        responseDto.setStatusSubKode(HentReviewResponseDto.StatusSubKode.EXCEPTION_THROWN);
        responseDto.setFejlBeskrivelse(e.getMessage());
        responseDto.setTransaktionsId(requestDto.getTransaktionsId());
        return responseDto;
    }
}
