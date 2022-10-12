package application.controller;

import application.dto.HentReviewRequestDto;
import application.dto.HentReviewResponseDto;
import application.dto.OpretReviewRequestDto;
import application.dto.OpretReviewResponseDto;
import application.service.ReviewReaderService;
import application.service.ReviewWriterService;
import io.swagger.annotations.SwaggerDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@SwaggerDefinition
public class ReviewController {

	// URL: http://localhost:8080/swagger-ui/

	private final ReviewWriterService reviewWriterService;
	private final ReviewReaderService reviewReaderService;

	@RequestMapping(method = RequestMethod.POST, value = "/opret")
	public OpretReviewResponseDto opret(OpretReviewRequestDto requestDto) {
		return reviewWriterService.opret(requestDto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hent")
	public HentReviewResponseDto hent(HentReviewRequestDto requestDto) {
		return reviewReaderService.hent(requestDto);
	}
}
