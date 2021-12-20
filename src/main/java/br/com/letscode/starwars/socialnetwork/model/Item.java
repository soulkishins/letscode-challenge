package br.com.letscode.starwars.socialnetwork.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "tb_item")
@SequenceGenerator(name = "sq_item", allocationSize = 1)
public class Item {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_item")
	private Integer id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "points")
	private byte points;
	
	@OneToMany(mappedBy = "id.item")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Inventory> inventory;

}
