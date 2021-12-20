package br.com.letscode.starwars.socialnetwork.dto;

import java.util.List;

import br.com.letscode.starwars.socialnetwork.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RebelDto {
	
	private Long id;

	private String name;

	private Short age;

	private GenderEnum gender;

	private RebelLocationDto location;

	private boolean traitor;
	
	private List<InventoryDto> inventory;

}
