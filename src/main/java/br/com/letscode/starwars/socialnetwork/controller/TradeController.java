package br.com.letscode.starwars.socialnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscode.starwars.socialnetwork.dto.TradeDto;
import br.com.letscode.starwars.socialnetwork.dto.TradeResultDto;
import br.com.letscode.starwars.socialnetwork.service.TradingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

	private final TradingService tradeService;

	@Operation(summary = "Do a trade between two rebels if both has the necessary items")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "202",
			description = "Trade accepted.",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = TradeResultDto.class)
				)
			}
		),
		@ApiResponse(
			responseCode = "400",
			description = "Invalid trade options, check response for more details.",
			content = {
				@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = TradeResultDto.class)
				)
			}
		)
	})
	@PostMapping
	public ResponseEntity<TradeResultDto> trade(@RequestBody TradeDto tradeData) {
		var tradeResult = tradeService.trade(tradeData);
		return ResponseEntity
			.status(tradeResult.isComplete() ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST)
			.body(tradeResult);
	}

}
