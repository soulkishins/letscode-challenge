package br.com.letscode.starwars.socialnetwork.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeDto {

	private long whoOffer;
	
	private List<SimpleInventoryDto> offerItems;

	private long whoAccept;

	private List<SimpleInventoryDto> acceptItems;

}
