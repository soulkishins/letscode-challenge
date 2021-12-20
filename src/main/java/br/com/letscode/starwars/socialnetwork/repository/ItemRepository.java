package br.com.letscode.starwars.socialnetwork.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.letscode.starwars.socialnetwork.model.Item;
import br.com.letscode.starwars.socialnetwork.model.ViewInventory;

@Repository
@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

	@Query("select it.id as itemID, it.name as itemName, sum(inv.stock) as stock, sum(inv.stock * it.points) as points from Item it join it.inventory inv join inv.id.rebel r where r.traitor = false group by it.id, it.name order by it.id")
	List<ViewInventory> listRebellionInventory();

	@Query("select it.id as itemID, it.name as itemName, sum(inv.stock) as stock, sum(inv.stock * it.points) as points from Item it join it.inventory inv join inv.id.rebel r where r.traitor = true group by it.id, it.name order by it.id")
	List<ViewInventory> listLostInventory();

}
