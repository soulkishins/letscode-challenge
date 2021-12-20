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
public class RebellionSituationDto {

	private long countRebels;

	private long countTraitors;

	private List<ReportInventoryDto> averageRebelInventory;

	private List<ReportInventoryDto> lostRebelInventory;

	private int totalPointsLost;

}
