package br.com.letscode.starwars.socialnetwork.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.InventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelDto;
import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.Rebel;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	uses = {
		RebelLocationMapper.class, InventoryMapper.class
	}
)
public interface RebelMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "traitor", constant = "false")
	Rebel dtoToEntity(CreateRebelDto rebel);

	Rebel dtoToEntity(RebelDto rebel);

	RebelDto dtoFromEntity(Rebel rebel);
	
	@Mapping(target = "inventory", source = "inventory", ignore = true)
	RebelDto dtoFromEntityWithoutIventory(Rebel rebel);

	Iterable<InventoryDto> mapInventoryEntity(Iterable<Inventory> list);

	Iterable<Inventory> mapInventoryDto(Iterable<InventoryDto> list);

}
