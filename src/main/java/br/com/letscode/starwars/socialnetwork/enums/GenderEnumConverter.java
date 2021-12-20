package br.com.letscode.starwars.socialnetwork.enums;

import java.util.Objects;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderEnumConverter implements AttributeConverter<GenderEnum, String> {

	@Override
	public String convertToDatabaseColumn(GenderEnum attribute) {
		if (Objects.nonNull(attribute)) {
			switch (attribute) {
			case FEMALE:
				return "F";
			case MALE:
				return "M";
			}
		}
		return null;
	}

	@Override
	public GenderEnum convertToEntityAttribute(String dbData) {
		if (Objects.nonNull(dbData)) {
			switch (dbData) {
			case "F":
				return GenderEnum.FEMALE;
			case "M":
				return GenderEnum.MALE;
			}
		}
		return null;
	}

}
