package br.com.letscode.starwars.socialnetwork.beans;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.enums.GenderEnum;

@RunWith(SpringRunner.class)
public class BuildersUnitTest {

	@Test
	public void whenBuilderCreateInstanceCheckValuesIsSet() {
		var testRebelBuilder = CreateRebelDto
		.builder()
		.name("Rebel Name")
		.age(Short.valueOf("30"))
		.gender(GenderEnum.FEMALE)
		.location(
			RebelLocationDto
			.builder()
			.latitude(0)
			.longitude(0)
			.baseName("Base Rebel")
			.build()
		)
		.inventory(
			Arrays.asList(
				SimpleInventoryDto.builder().itemID(3).stock(10).build(),
				SimpleInventoryDto.builder().itemID(4).stock(10).build()
			)
		);
		
		assertThat(testRebelBuilder.toString()).isNotEmpty();
		var testRebel = testRebelBuilder.build();
        assertThat(testRebel).isNotNull();
        assertThat(testRebel.getName()).isEqualTo("Rebel Name");
        assertThat(testRebel.getAge()).isEqualTo(Short.valueOf("30"));
        assertThat(testRebel.getGender()).isEqualTo(GenderEnum.FEMALE);
        assertThat(testRebel.getLocation()).isNotNull();
        assertThat(testRebel.getInventory()).isNotNull();
	}

}
