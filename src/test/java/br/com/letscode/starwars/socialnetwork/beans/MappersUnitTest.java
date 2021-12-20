package br.com.letscode.starwars.socialnetwork.beans;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.InventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.mapper.InventoryMapper;
import br.com.letscode.starwars.socialnetwork.mapper.RebelLocationMapper;
import br.com.letscode.starwars.socialnetwork.mapper.RebelMapper;
import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.InventoryID;
import br.com.letscode.starwars.socialnetwork.model.Rebel;
import br.com.letscode.starwars.socialnetwork.model.RebelLocation;
import br.com.letscode.starwars.socialnetwork.model.ViewInventory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MappersUnitTest {
	
	@Autowired
	private InventoryMapper inventoryMapper;
	@Autowired
	private RebelLocationMapper rebelLocationMapper;
	@Autowired
	private RebelMapper rebelMapper;

	@Test
	public void whenNeedConvert_thenShouldCallConvert() {

		assertThat(inventoryMapper.dtoFromEntity(null)).isNull();
		assertThat(inventoryMapper.dtoFromEntity(Inventory.builder().id(InventoryID.builder().build()).build())).isNotNull();
		assertThat(inventoryMapper.dtoToEntity((InventoryDto) null)).isNull();
		assertThat(inventoryMapper.dtoToEntity(InventoryDto.builder().itemID(0).itemName("Name").build())).isNotNull();
		assertThat(inventoryMapper.dtoToEntity((SimpleInventoryDto) null)).isNull();
		assertThat(inventoryMapper.dtoToEntity(SimpleInventoryDto.builder().build())).isNotNull();
		assertThat(inventoryMapper.listDtoFromListView(null)).isNull();
		assertThat(inventoryMapper.listDtoFromListView(
			Collections.singletonList(
				new ViewInventory() {
					@Override public int getStock() { return 0; }
					@Override public int getPoints() { return 0; }
					@Override public String getItemName() { return null; }
					@Override public int getItemID() { return 0; }
				}
			)
		)).isNotNull();
		assertThat(inventoryMapper.dtoFromView(null)).isNull();

		assertThat(rebelLocationMapper.dtoFromEntity(null)).isNull();
		assertThat(rebelLocationMapper.dtoFromEntity(RebelLocation.builder().build())).isNotNull();
		assertThat(rebelLocationMapper.dtoToEntity(null)).isNull();
		assertThat(rebelLocationMapper.dtoToEntity(RebelLocationDto.builder().build())).isNotNull();
		
		assertThat(rebelMapper.dtoFromEntity(null)).isNull();
		assertThat(rebelMapper.dtoFromEntity(
			Rebel
			.builder()
			.location(
				RebelLocation.builder().build()
			)
			.inventory(Collections.singletonList(Inventory.builder().id(InventoryID.builder().build()).build()))
			.build()
		)).isNotNull();
		assertThat(rebelMapper.dtoFromEntityWithoutIventory(null)).isNull();
		assertThat(rebelMapper.dtoFromEntityWithoutIventory(
			Rebel
			.builder()
			.location(
				RebelLocation.builder().build()
			)
			.build()
		)).isNotNull();
		assertThat(rebelMapper.dtoToEntity((RebelDto) null)).isNull();
		assertThat(rebelMapper.dtoToEntity(
			RebelDto
			.builder()
			.location(
				RebelLocationDto.builder().build()
			)
			.inventory(Collections.singletonList(InventoryDto.builder().build()))
			.build()
		)).isNotNull();
		assertThat(rebelMapper.dtoToEntity((CreateRebelDto) null)).isNull();
		assertThat(rebelMapper.dtoToEntity(
			CreateRebelDto
			.builder()
			.location(
				RebelLocationDto.builder().build()
			)
			.inventory(Collections.singletonList(SimpleInventoryDto.builder().build()))
			.build()
		)).isNotNull();
		
	}

}
