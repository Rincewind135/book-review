package application.controller;

import application.dto.*;
import application.service.ReviewReaderService;
import application.service.ReviewWriterService;
import io.swagger.annotations.SwaggerDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@SwaggerDefinition
public class ReviewController extends Controller {

	private final Logger logger = Logger.getLogger(ReviewController.class.getName());

	private final ReviewWriterService reviewWriterService;
	private final ReviewReaderService reviewReaderService;

	@RequestMapping(method = RequestMethod.POST, value = "/opret")
	public ResponseEntity<OpretReviewResponseDto> opret(@RequestBody @Valid OpretReviewRequestDto requestDto, @ApiIgnore Errors errors) {
		logger.log(Level.INFO, "Request modtaget: " + requestDto.toString());
		if (errors.hasErrors()) {
			logger.log(Level.WARNING, "Fejl fundet i kald til opret: " + errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		OpretReviewResponseDto serviceResult = reviewWriterService.opret(requestDto);
		HttpStatus httpStatus = getHttpStatus(serviceResult);
		return ResponseEntity.status(httpStatus).body(serviceResult);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hent")
	public ResponseEntity<HentReviewResponseDto> hent(@RequestBody @Valid HentReviewRequestDto requestDto, @ApiIgnore Errors errors) {
		logger.log(Level.INFO, "Request modtaget: " + requestDto.toString());
		if (errors.hasErrors()) {
			logger.log(Level.WARNING, "Fejl fundet i kald til hent: " + errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		HentReviewResponseDto serviceResult = reviewReaderService.hent(requestDto);
		HttpStatus httpStatus = getHttpStatus(serviceResult);
		return ResponseEntity.status(httpStatus).body(serviceResult);
	}
}
