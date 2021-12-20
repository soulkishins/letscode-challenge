package br.com.letscode.starwars.socialnetwork.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.letscode.starwars.socialnetwork.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_rebel")
@SequenceGenerator(name = "sq_rebel", allocationSize = 1)
public class Rebel {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_rebel")
	private Long id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "age")
	private Short age;

	@Column(name = "gender")
	private GenderEnum gender;

	@Embedded
	private RebelLocation location;

	@Column(name = "bl_traitor", nullable = false)
	private boolean traitor;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(insertable = false, updatable = false, name = "rebel_id", referencedColumnName = "id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Inventory> inventory;

}
