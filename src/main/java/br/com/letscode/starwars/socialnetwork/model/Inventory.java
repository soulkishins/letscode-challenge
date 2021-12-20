package br.com.letscode.starwars.socialnetwork.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_inventory")
public class Inventory implements Persistable<InventoryID> {

	@EmbeddedId
	private InventoryID id;

	@Column(name = "stock", nullable = false)
	private int stock;
	
	@Transient
	@Builder.Default
	private boolean isNew = true;
	
	@PostLoad
	public void markNotNew() {
		isNew = false;
	}

}
