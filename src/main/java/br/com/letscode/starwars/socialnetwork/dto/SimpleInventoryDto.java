package br.com.letscode.starwars.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleInventoryDto {

	private int itemID;

	private int stock;

}
