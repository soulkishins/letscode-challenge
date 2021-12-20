package br.com.letscode.starwars.socialnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.ExceptionDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.dto.TraitorReportDto;
import br.com.letscode.starwars.socialnetwork.service.RebellionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rebels")
@RequiredArgsConstructor
public class RebelController {
	
	private final RebellionService rebelService;

	@Operation(summary = "Insert new rebel with your inventory and position in galaxy")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "New rebel created",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = RebelDto.class)
				)
			}
		)
	})
	@PostMapping
	public ResponseEntity<RebelDto> createRebel(@RequestBody CreateRebelDto rebel) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(rebelService.createRebel(rebel));
	}

	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Position updated.",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = RebelDto.class)
				)
			}
		),
		@ApiResponse(
			responseCode = "404",
			description = "Rebel not found",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ExceptionDto.class)
				)
			}
		)
	})
	@Operation(summary = "Update rebel`s position in galaxy")
	@PatchMapping("/{id}/location")
	public ResponseEntity<RebelDto> updateRebelLocation(@PathVariable("id") long rebelID, @RequestBody RebelLocationDto location) {
		return ResponseEntity.ok(rebelService.updateRebelLocation(rebelID, location));
	}

	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "Report complete.",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = RebelDto.class)
				)
			}
		),
		@ApiResponse(
			responseCode = "404",
			description = "Rebel not found",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = ExceptionDto.class)
				)
			}
		)
	})
	@Operation(summary = "Report rebel as traitor and update your status if necessary")
	@PutMapping("/{id}/traitor-report")
	public ResponseEntity<RebelDto> reportRebelTraitor(@PathVariable("id") long rebelID, @RequestBody TraitorReportDto report) {
		return ResponseEntity.ok(rebelService.reportRebelTraitor(rebelID, report.getReporter()));
	}

}
