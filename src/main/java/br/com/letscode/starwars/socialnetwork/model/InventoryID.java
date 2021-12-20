package br.com.letscode.starwars.socialnetwork.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class InventoryID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rebel_id", referencedColumnName = "id", updatable = false)
	private Rebel rebel;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id", referencedColumnName = "id", updatable = false)
	private Item item;

}
