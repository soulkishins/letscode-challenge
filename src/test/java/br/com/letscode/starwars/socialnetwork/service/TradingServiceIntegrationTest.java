package br.com.letscode.starwars.socialnetwork.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.TradeDto;
import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.InventoryID;
import br.com.letscode.starwars.socialnetwork.model.Item;
import br.com.letscode.starwars.socialnetwork.model.Rebel;
import br.com.letscode.starwars.socialnetwork.repository.InventoryRepository;
import br.com.letscode.starwars.socialnetwork.repository.ItemRepository;
import br.com.letscode.starwars.socialnetwork.repository.RebelRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradingServiceIntegrationTest {
	
	@Autowired
	private TradingService tradingService;

	@MockBean
	private RebelRepository rebelRepository;
	@MockBean
	private InventoryRepository inventoryRepository;
	@MockBean
	private ItemRepository itemRepository;

	@Before
	public void setUp() {
		Item item1 = Item.builder().id(1).name("Item 1 Test").points((byte) 1).build();
		Item item2 = Item.builder().id(2).name("Item 2 Test").points((byte) 2).build();
		Item item3 = Item.builder().id(3).name("Item 3 Test").points((byte) 1).build();
		Item item4 = Item.builder().id(4).name("Item 4 Test").points((byte) 2).build();
		
		List<Inventory> inventoryWhoOffer = new ArrayList<>();
		inventoryWhoOffer.add(Inventory.builder().id(InventoryID.builder().item(item1).build()).stock(50).build());
		List<Inventory> inventoryWhoAccept = new ArrayList<>();
		inventoryWhoAccept.add(Inventory.builder().id(InventoryID.builder().item(item2).build()).stock(10).build());

		Rebel whoOffer = Rebel.builder().id(1L).inventory(inventoryWhoOffer).build();
		Rebel whoAccept = Rebel.builder().id(2L).inventory(inventoryWhoAccept).build();
		Rebel traitor = Rebel.builder().id(3L).traitor(true).build();

		Specification<Inventory> specWhoOffer = (root, query, criteriaBuilder) -> null;
		Specification<Inventory> specWhoAccept = (root, query, criteriaBuilder) -> null;

		Mockito.when(rebelRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(whoOffer));
		Mockito.when(rebelRepository.findById(Mockito.eq(2L))).thenReturn(Optional.of(whoAccept));
		Mockito.when(rebelRepository.findById(Mockito.eq(3L))).thenReturn(Optional.of(traitor));
		Mockito.when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2, item3, item4));
		Mockito.when(inventoryRepository.findByRebel(Mockito.eq(1L))).thenReturn(specWhoOffer);
		Mockito.when(inventoryRepository.findByRebel(Mockito.eq(2L))).thenReturn(specWhoAccept);
		Mockito.when(inventoryRepository.findAll(Mockito.eq(specWhoOffer))).thenReturn(inventoryWhoOffer);
		Mockito.when(inventoryRepository.findAll(Mockito.eq(specWhoAccept))).thenReturn(inventoryWhoAccept);
	}

	@Test
	public void whenValid_thenTradingShouldBeComplete() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(2).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(1).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Trade Complete.");
		assertThat(trading.isComplete()).isEqualTo(true);
	}
	
	@Test
	public void whenWhoOfferNotFound_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1000L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(2).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(1).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Rebel who offered not found.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}
	
	@Test
	public void whenWhoOfferIsTraitor_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(3L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(2).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(1).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Rebel who offered is traitor.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}
	
	@Test
	public void whenWhoAcceptNotFound_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2000L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(2).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(1).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Rebel who accepted not found.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}
	
	@Test
	public void whenWhoAcceptIsTraitor_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(3L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(2).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(1).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Rebel who accepted is traitor.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}
	
	@Test
	public void whenIllegalStock_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(-1).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(1).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Illegal trade stock.");
		assertThat(trading.isComplete()).isEqualTo(false);
		trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(2).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(-1).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Illegal trade stock.");
		assertThat(trading.isComplete()).isEqualTo(false);
		trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(1).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(1).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Illegal trade points.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}
	
	@Test
	public void whenOffetNotHaveStock_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(200).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(100).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Rebel who offered don`t have enough items.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}
	
	@Test
	public void whenOffetNotHaveStockV2_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(4).stock(100).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(100).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Rebel who offered don`t have enough items.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}
	
	@Test
	public void whenAcceptNotHaveStock_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(40).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(2).stock(20).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Rebel who accepted don`t have enough items.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}
	
	@Test
	public void whenAcceptNotHaveStockV2_thenTradingShouldBeError() {
		var trading = tradingService.trade(
			TradeDto
			.builder()
			.whoOffer(1L)
			.whoAccept(2L)
			.offerItems(Arrays.asList(SimpleInventoryDto.builder().itemID(1).stock(40).build()))
			.acceptItems(Arrays.asList(SimpleInventoryDto.builder().itemID(3).stock(40).build()))
			.build()
		);
		assertThat(trading.getMessage()).isEqualTo("Rebel who accepted don`t have enough items.");
		assertThat(trading.isComplete()).isEqualTo(false);
	}

}
