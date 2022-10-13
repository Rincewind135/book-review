package application.controller;

import application.dto.*;
import application.service.BogReaderService;
import application.service.BogWriterService;
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
@RequestMapping("/bog")
@SwaggerDefinition
public class BogController extends Controller {

	private final Logger logger = Logger.getLogger(BogController.class.getName());

	private final BogReaderService bogReaderService;
	private final BogWriterService bogWriterService;

	@RequestMapping(method = RequestMethod.POST, value = "/opret")
	public ResponseEntity<OpretBogResponseDto> opret(@RequestBody @Valid OpretBogRequestDto requestDto, @ApiIgnore Errors errors) {
		logger.log(Level.INFO, "Request modtaget: " + requestDto.toString());
		if (errors.hasErrors()) {
			logger.log(Level.WARNING, "Fejl fundet i kald til opret: " + errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		OpretBogResponseDto serviceResult = bogWriterService.opret(requestDto);
		HttpStatus httpStatus = getHttpStatus(serviceResult);
		return ResponseEntity.status(httpStatus).body(serviceResult);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hent")
	public ResponseEntity<HentBogResponseDto> hent(@RequestBody @Valid HentBogRequestDto requestDto, @ApiIgnore Errors errors) {
		logger.log(Level.INFO, "Request modtaget: " + requestDto.toString());
		if (errors.hasErrors()) {
			logger.log(Level.WARNING, "Fejl fundet i kald til hent: " + errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		HentBogResponseDto serviceResult = bogReaderService.hent(requestDto);
		HttpStatus httpStatus = getHttpStatus(serviceResult);
		return ResponseEntity.status(httpStatus).body(serviceResult);
	}
}
