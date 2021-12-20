package br.com.letscode.starwars.socialnetwork.beans;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.letscode.starwars.socialnetwork.enums.GenderEnum;
import br.com.letscode.starwars.socialnetwork.enums.GenderEnumConverter;

@RunWith(SpringRunner.class)
public class ConvertersUnitTest {

	private GenderEnumConverter genderEnumConverter = new GenderEnumConverter();

	@Test
	public void whenNeedConvertDBType_thenShouldCallConvert() {

		assertThat(genderEnumConverter.convertToDatabaseColumn(null)).isNull();
		assertThat(genderEnumConverter.convertToDatabaseColumn(GenderEnum.FEMALE)).isEqualTo("F");
		assertThat(genderEnumConverter.convertToDatabaseColumn(GenderEnum.MALE)).isEqualTo("M");

		assertThat(genderEnumConverter.convertToEntityAttribute(null)).isNull();
		assertThat(genderEnumConverter.convertToEntityAttribute("F")).isEqualTo(GenderEnum.FEMALE);
		assertThat(genderEnumConverter.convertToEntityAttribute("M")).isEqualTo(GenderEnum.MALE);

	}

}
