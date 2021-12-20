package br.com.letscode.starwars.socialnetwork.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.letscode.starwars.socialnetwork.enums.GenderEnum;
import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.InventoryID;
import br.com.letscode.starwars.socialnetwork.model.Item;
import br.com.letscode.starwars.socialnetwork.model.Rebel;
import br.com.letscode.starwars.socialnetwork.model.RebelLocation;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ItemRepository itemRepository;
  
	@Before
	public void createMockDataAndCheckEqualsHashCodeToString() {
		Item item = Item.builder().name("Item Test").points((byte) 10).build();
		Item recordItem = entityManager.persistAndFlush(item);
      
		var testRebel1 = Rebel
		.builder()
		.name("Rebel Test 1")
		.age(Short.valueOf("30"))
		.gender(GenderEnum.MALE)
		.location(
			RebelLocation
			.builder()
			.latitude(10)
			.longitude(20)
			.baseName("Base Rebel")
			.build()
		)
		.inventory(
			Arrays.asList(
				Inventory.builder().id(InventoryID.builder().item(recordItem).build()).stock(10).build()
			)
		)
		.build();
		testRebel1.getInventory().get(0).getId().setRebel(testRebel1);
		
		var testRebel2 = Rebel
		.builder()
		.name("Rebel Test 2")
		.age(Short.valueOf("30"))
		.gender(GenderEnum.MALE)
		.location(
			RebelLocation
			.builder()
			.latitude(10)
			.longitude(20)
			.baseName("Base Rebel")
			.build()
		)
		.inventory(
			Arrays.asList(
				Inventory.builder().id(InventoryID.builder().item(recordItem).build()).stock(20).build()
			)
		)
		.build();
		testRebel2.getInventory().get(0).getId().setRebel(testRebel2);
		
		var testTraitor = Rebel
		.builder()
		.name("Traitor Test")
		.age(Short.valueOf("20"))
		.gender(GenderEnum.FEMALE)
		.traitor(true)
		.location(
			RebelLocation
			.builder()
			.latitude(10)
			.longitude(20)
			.baseName("Base Rebel")
			.build()
		)
		.inventory(
			Arrays.asList(
				Inventory.builder().id(InventoryID.builder().item(recordItem).build()).stock(10).build()
			)
		)
		.build();
		testTraitor.getInventory().get(0).getId().setRebel(testTraitor);

		entityManager.persist(testRebel1);
		entityManager.persist(testRebel2);
		entityManager.persist(testTraitor);
		entityManager.flush();
	}
	
	@Test
	public void whenUseModel_needOverrideEqualHashCodeToString() {
		Item item = Item.builder().name("Item Test").points((byte) 10).build();
		Item recordItem = entityManager.persistAndFlush(item);

		assertThat(item).isEqualTo(recordItem);
		assertThat(item).hasSameHashCodeAs(recordItem);
		assertThat(item.toString()).isNotEmpty();
	}

	@Test
	public void whenListLostInventory_thenReturnTraitorItem() {
		var lostInventory = itemRepository.listLostInventory();
		assertThat(lostInventory.get(0).getItemName()).isEqualTo("Item Test");
		assertThat(lostInventory.get(0).getStock()).isEqualTo(10);
		assertThat(lostInventory.get(0).getPoints()).isEqualTo(100);
	}

	@Test
	public void whenListRebellionInventory_thenReturnRebellionItems() {
		var rebellionInventory = itemRepository.listRebellionInventory();
		assertThat(rebellionInventory.get(0).getItemName()).isEqualTo("Item Test");
		assertThat(rebellionInventory.get(0).getStock()).isEqualTo(30);
		assertThat(rebellionInventory.get(0).getPoints()).isEqualTo(300);
	}

}
