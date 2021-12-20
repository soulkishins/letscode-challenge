package br.com.letscode.starwars.socialnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscode.starwars.socialnetwork.dto.RebellionSituationDto;
import br.com.letscode.starwars.socialnetwork.service.RebellionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/situation")
@RequiredArgsConstructor
public class SituationController {

	private final RebellionService rebelService;

	@Operation(summary = "View actual Rebellion situation")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "View success",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = RebellionSituationDto.class)
				)
			}
		)
	})
	@GetMapping
	public ResponseEntity<RebellionSituationDto> reportSituation() {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(rebelService.reportSituation());
	}

}
