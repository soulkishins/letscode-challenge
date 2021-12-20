package br.com.letscode.starwars.socialnetwork.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.InventoryID;

@Repository
@Transactional(readOnly = true)
public interface InventoryRepository extends JpaRepository<Inventory, InventoryID>, JpaSpecificationExecutor<Inventory> {
	
	default Specification<Inventory> findByRebel(long rebel) {
		return (root, query, builder) -> {
			return builder.equal(root.get("id").get("rebel").get("id"), rebel);
		};
	}

}
