package br.com.letscode.starwars.socialnetwork.mapper;

import org.mapstruct.Mapper;

import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.model.RebelLocation;

@Mapper(
	componentModel = "spring"
)
public interface RebelLocationMapper {

	RebelLocation dtoToEntity(RebelLocationDto location);

	RebelLocationDto dtoFromEntity(RebelLocation location);
	
}
