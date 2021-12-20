package br.com.letscode.starwars.socialnetwork.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.letscode.starwars.socialnetwork.model.Rebel;
import br.com.letscode.starwars.socialnetwork.model.RebelLocation;
import br.com.letscode.starwars.socialnetwork.model.TraitorReport;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TraitorReportRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TraitorReportRepository reportRepository;
	
	@Test
	public void whenUseModel_needOverrideEqualHashCodeToString() {
		
		var rebel = Rebel.builder()
				.name("Rebel Test")
				.location(
					RebelLocation
					.builder()
					.latitude(10)
					.longitude(20)
					.baseName("Base Name")
					.build()
				).build();
		
		var reporter = Rebel.builder()
				.name("Report Test")
				.location(
					RebelLocation
					.builder()
					.latitude(10)
					.longitude(20)
					.baseName("Base Name")
					.build()
				).build();
		
		var model = TraitorReport
			.builder()
			.traitor(entityManager.persistAndFlush(rebel))
			.reporter(entityManager.persistAndFlush(reporter))
			.build();
		var record = entityManager.persistAndFlush(model);

		assertThat(model).isEqualTo(record);
		assertThat(model).hasSameHashCodeAs(record);
		assertThat(model.toString()).isNotEmpty();

	}
	
	@Test
	public void whenFilterReporter_thenShouldCreateSpec() {
		var entityMngr = entityManager.getEntityManager();
		var builder = entityMngr.getCriteriaBuilder();
		var criteria = builder.createQuery(TraitorReport.class);
		var root = criteria.from(TraitorReport.class);

		assertThat(
			reportRepository
			.findByReporter(1)
			.toPredicate(
				root,
				criteria,
				builder
			)
		).isNotNull();
	}
	
	@Test
	public void whenFilterTraitor_thenShouldCreateSpec() {
		var entityMngr = entityManager.getEntityManager();
		var builder = entityMngr.getCriteriaBuilder();
		var criteria = builder.createQuery(TraitorReport.class);
		var root = criteria.from(TraitorReport.class);

		assertThat(
			reportRepository
			.findByTraitor(1)
			.toPredicate(
				root,
				criteria,
				builder
			)
		).isNotNull();
	}

}
