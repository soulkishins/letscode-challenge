package br.com.letscode.starwars.socialnetwork.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RebelLocation {

	@Column(name = "latitude", nullable = false)
	private long latitude;

	@Column(name = "longitude", nullable = false)
	private long longitude;

	@Column(name = "basename", nullable = false, length = 50)
	private String baseName;

}
