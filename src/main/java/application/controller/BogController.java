package application.controller;

import application.dto.HentBogRequestDto;
import application.dto.HentBogResponseDto;
import application.dto.OpretBogRequestDto;
import application.dto.OpretBogResponseDto;
import application.service.BogReaderService;
import application.service.BogWriterService;
import io.swagger.annotations.SwaggerDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bog")
@SwaggerDefinition
public class BogController {

	// URL: http://localhost:8080/swagger-ui/

	private final BogReaderService bogReaderService;
	private final BogWriterService bogWriterService;

	@RequestMapping(method = RequestMethod.POST, value = "/opret")
	public OpretBogResponseDto opret(OpretBogRequestDto requestDto) {
		return bogWriterService.opret(requestDto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hent")
	public HentBogResponseDto hent(HentBogRequestDto requestDto) {
		return bogReaderService.hent(requestDto);
	}
}
