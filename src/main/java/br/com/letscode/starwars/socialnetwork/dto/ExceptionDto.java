package br.com.letscode.starwars.socialnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDto {

	private String message;

	private int status;

}
