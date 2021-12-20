package br.com.letscode.starwars.socialnetwork.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import br.com.letscode.starwars.socialnetwork.dto.InventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.ReportInventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.ViewInventory;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

	@Mapping(source = "itemID", target = "id.item.id")
	@Mapping(target = "isNew", constant = "true")
	Inventory dtoToEntity(InventoryDto inventory);

	@Mapping(source = "itemID", target = "id.item.id")
	@Mapping(target = "isNew", constant = "true")
	Inventory dtoToEntity(SimpleInventoryDto inventory);

	@Mapping(source = "id.item.id", target = "itemID", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
	@Mapping(source = "id.item.name", target = "itemName", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
	InventoryDto dtoFromEntity(Inventory inventory);
	
	List<ReportInventoryDto> listDtoFromListView(List<ViewInventory> inventory);

	ReportInventoryDto dtoFromView(ViewInventory inventory);

}
