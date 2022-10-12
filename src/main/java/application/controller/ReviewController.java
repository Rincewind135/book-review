package application.controller;

import application.dto.OpretReviewRequestDto;
import application.dto.OpretReviewResponseDto;
import application.service.ReviewService;
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

	private final ReviewService reviewService;

	@RequestMapping(method = RequestMethod.POST, value = "/opret")
	public OpretReviewResponseDto opret(OpretReviewRequestDto requestDto) {
		return reviewService.opret(requestDto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hent")
	public String hent() {
		return "The Dark Tower";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/welcome")
	public String welcome() {
		return "Hello to my book review service";
	}
}
