package br.com.letscode.starwars.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportInventoryDto {

	private int itemID;

	private String itemName;

	private int stock;

	private int points;

}
