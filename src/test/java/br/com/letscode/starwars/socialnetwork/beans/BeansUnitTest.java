package br.com.letscode.starwars.socialnetwork.beans;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.ExceptionDto;
import br.com.letscode.starwars.socialnetwork.dto.InventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.dto.RebellionSituationDto;
import br.com.letscode.starwars.socialnetwork.dto.ReportInventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.TradeDto;
import br.com.letscode.starwars.socialnetwork.dto.TradeResultDto;
import br.com.letscode.starwars.socialnetwork.dto.TraitorReportDto;
import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.InventoryID;
import br.com.letscode.starwars.socialnetwork.model.Item;
import br.com.letscode.starwars.socialnetwork.model.Rebel;
import br.com.letscode.starwars.socialnetwork.model.RebelLocation;
import br.com.letscode.starwars.socialnetwork.model.TraitorReport;

@RunWith(SpringRunner.class)
public class BeansUnitTest {

	@Test
	public void givenDtoValidFullManipulation() {
		assertThat(
			CreateRebelDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			ExceptionDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			InventoryDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			RebelDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			RebellionSituationDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			RebelLocationDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			ReportInventoryDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			SimpleInventoryDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			TradeDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			TradeResultDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
		assertThat(
			TraitorReportDto.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidBeanEquals(),
				hasValidGettersAndSetters(),
				hasValidBeanHashCode(),
				hasValidBeanToString()
			)
		);
	}
	
	@Test
	public void givenModelValidFullManipulation() {
		assertThat(
			Inventory.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidGettersAndSetters()
			)
		);
		assertThat(
			InventoryID.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidGettersAndSetters()
			)
		);
		assertThat(
			Item.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidGettersAndSetters()
			)
		);
		assertThat(
			Rebel.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidGettersAndSetters()
			)
		);
		assertThat(
			RebelLocation.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidGettersAndSetters()
			)
		);
		assertThat(
			TraitorReport.class,
			allOf(
				hasValidBeanConstructor(),
				hasValidGettersAndSetters()
			)
		);
	}

}
