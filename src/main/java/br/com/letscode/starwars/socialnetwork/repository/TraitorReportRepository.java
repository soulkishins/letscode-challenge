package br.com.letscode.starwars.socialnetwork.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.letscode.starwars.socialnetwork.model.TraitorReport;

@Repository
@Transactional(readOnly = true)
public interface TraitorReportRepository extends JpaRepository<TraitorReport, Long>, JpaSpecificationExecutor<TraitorReport> {
	
	default Specification<TraitorReport> findByTraitor(long traitor) {
		return (root, query, builder) -> {
			return builder.equal(root.get("traitor").get("id"), traitor);
		};
	}
	
	default Specification<TraitorReport> findByReporter(long reporter) {
		return (root, query, builder) -> {
			return builder.equal(root.get("reporter").get("id"), reporter);
		};
	}

}
