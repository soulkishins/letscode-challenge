package br.com.letscode.starwars.socialnetwork.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

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
public class RebelRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RebelRepository rebelRepository;
	
	@Test
	public void whenUseModel_needOverrideEqualHashCodeToString() {
		
		var item = entityManager.persistAndFlush(Item.builder().name("Item").build());
		
		var model = Rebel
			.builder()
			.name("Rebel Test")
			.age(Short.valueOf("20"))
			.location(
				RebelLocation
				.builder()
				.latitude(10)
				.longitude(20)
				.baseName("Base Name")
				.build()
			)
			.inventory(Collections.singletonList(
				Inventory
				.builder()
				.id(
					InventoryID
					.builder()
					.item(item)
					.build())
				.stock(10)
				.build()
			))
			.build();
		model.getInventory().get(0).getId().setRebel(model);
		var record = entityManager.persistAndFlush(model);

		record.setGender(GenderEnum.MALE);
		assertThat(model).isEqualTo(record);
		assertThat(model).hasSameHashCodeAs(record);
		assertThat(model.toString()).isNotEmpty();
	}
	
	@Test
	public void whenFilterTraitor_thenShouldCreateSpec() {
		var entityMngr = entityManager.getEntityManager();
		var builder = entityMngr.getCriteriaBuilder();
		var criteria = builder.createQuery(Rebel.class);
		var root = criteria.from(Rebel.class);

		assertThat(
			rebelRepository
			.findByIsTraitor()
			.toPredicate(
				root,
				criteria,
				builder
			)
		).isNotNull();
	}
	
	@Test
	public void whenFilterNotTraitor_thenShouldCreateSpec() {
		var entityMngr = entityManager.getEntityManager();
		var builder = entityMngr.getCriteriaBuilder();
		var criteria = builder.createQuery(Rebel.class);
		var root = criteria.from(Rebel.class);

		assertThat(
			rebelRepository
			.findByIsNotTraitor()
			.toPredicate(
				root,
				criteria,
				builder
			)
		).isNotNull();
	}

}
