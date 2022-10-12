package application.controller;

import application.dto.OpretBogRequestDto;
import application.dto.OpretBogResponseDto;
import application.service.BogService;
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

	private final BogService bogService;

	@RequestMapping(method = RequestMethod.POST, value = "/opret")
	public OpretBogResponseDto opret(OpretBogRequestDto requestDto) {
		return bogService.opret(requestDto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/hent")
	public String hent() {
		return bogService.findBogById(null).get().getTitel();
	}
}
