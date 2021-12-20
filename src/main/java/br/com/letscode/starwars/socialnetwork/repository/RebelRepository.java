package br.com.letscode.starwars.socialnetwork.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.letscode.starwars.socialnetwork.model.Rebel;

@Repository
@Transactional(readOnly = true)
public interface RebelRepository extends JpaRepository<Rebel, Long>, JpaSpecificationExecutor<Rebel> {
	default Specification<Rebel> findByIsTraitor() {
		return (root, query, builder) -> {
			return builder.equal(root.get("traitor"), true);
		};
	}
	
	default Specification<Rebel> findByIsNotTraitor() {
		return (root, query, builder) -> {
			return builder.equal(root.get("traitor"), false);
		};
	}
}
