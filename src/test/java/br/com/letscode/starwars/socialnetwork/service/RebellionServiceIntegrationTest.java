package br.com.letscode.starwars.socialnetwork.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.model.Rebel;
import br.com.letscode.starwars.socialnetwork.model.TraitorReport;
import br.com.letscode.starwars.socialnetwork.model.ViewInventory;
import br.com.letscode.starwars.socialnetwork.repository.ItemRepository;
import br.com.letscode.starwars.socialnetwork.repository.RebelRepository;
import br.com.letscode.starwars.socialnetwork.repository.TraitorReportRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RebellionServiceIntegrationTest {

	@Autowired
	private RebellionService rebellionService;
	
	@MockBean
	private RebelRepository rebelRepository;
	@MockBean
	private TraitorReportRepository traitorReportRepository;
	@MockBean
	private ItemRepository itemRepository;

	@Before
	public void setUp() {
		Rebel rebel1 = Rebel.builder().id(1L).build();
		Rebel rebel2 = Rebel.builder().id(2L).build();
		Rebel rebel3 = Rebel.builder().id(3L).build();
		Rebel traitor = Rebel.builder().id(4L).traitor(true).build();

		Mockito.when(rebelRepository.save(Mockito.any())).thenReturn(rebel1);
		Mockito.when(rebelRepository.findById(Mockito.eq(1L))).thenReturn(Optional.of(rebel1));
		Mockito.when(rebelRepository.findById(Mockito.eq(2L))).thenReturn(Optional.of(rebel2));
		Mockito.when(rebelRepository.findById(Mockito.eq(3L))).thenReturn(Optional.of(rebel3));
		Mockito.when(rebelRepository.findById(Mockito.eq(4L))).thenReturn(Optional.of(traitor));
		
		Specification<Rebel> isNotTraitor = (root, query, criteriaBuilder) ->  null;
		Specification<Rebel> isTraitor = (root, query, criteriaBuilder) ->  null;
		
		Mockito.when(rebelRepository.findByIsNotTraitor()).thenReturn(isNotTraitor);
		Mockito.when(rebelRepository.findByIsTraitor()).thenReturn(isTraitor);
		Mockito.when(rebelRepository.count(Mockito.eq(isNotTraitor))).thenReturn(1l);
		Mockito.when(rebelRepository.count(Mockito.eq(isTraitor))).thenReturn(2l);

		ViewInventory inventory = new ViewInventory() {
			@Override public int getStock() { return 1; }
			@Override public int getPoints() { return 2; }
			@Override public String getItemName() { return "Item Test"; }
			@Override public int getItemID() { return 1; }
		};
		Mockito.when(itemRepository.listRebellionInventory()).thenReturn(Collections.singletonList(inventory));
		Mockito.when(itemRepository.listLostInventory()).thenReturn(Arrays.asList(inventory, inventory));

		Specification<TraitorReport> specWhere1 = (root, query, criteriaBuilder) -> null;
		Specification<TraitorReport> specWhere2 = (root, query, criteriaBuilder) -> null;
		Specification<TraitorReport> specWhere3 = new Specification<TraitorReport>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<TraitorReport> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				return null;
			}
			@Override
			public Specification<TraitorReport> and(Specification<TraitorReport> other) {
				return this;
			}
		};
		Mockito.when(traitorReportRepository.findByReporter(1L)).thenReturn(specWhere1);
		Mockito.when(traitorReportRepository.findByReporter(2L)).thenReturn(specWhere2);
		Mockito.when(traitorReportRepository.findByReporter(3L)).thenReturn(specWhere3);
		Mockito.when(traitorReportRepository.findByTraitor(1L)).thenReturn(specWhere1);
		Mockito.when(traitorReportRepository.findByTraitor(2L)).thenReturn(specWhere2);
		Mockito.when(traitorReportRepository.findByTraitor(3L)).thenReturn(specWhere3);
		
		Mockito.when(traitorReportRepository.count(
			Mockito.eq(
				specWhere1
				.and(specWhere2)
			)
		)).thenReturn(Long.valueOf(0L));
		
		Mockito.when(traitorReportRepository.count(
			Mockito.eq(
				specWhere2
				.and(specWhere1)
			)
		)).thenReturn(Long.valueOf(0L));

		Mockito.when(traitorReportRepository.count(
			Mockito.eq(
				specWhere3
				.and(specWhere1)
			)
		)).thenReturn(Long.valueOf(1L));
		
		Mockito.when(traitorReportRepository.count(
			Mockito.eq(
				specWhere1
			)
		)).thenReturn(Long.valueOf(0L));
		
		Mockito.when(traitorReportRepository.count(
			Mockito.eq(
				specWhere2
			)
		)).thenReturn(Long.valueOf(3L));

	}

	@Test
	public void whenValid_thenRebelShouldBeCreate() {
		RebelDto created = rebellionService.createRebel(
			CreateRebelDto
			.builder()
			.name("Rebel Test")
			.inventory(Arrays.asList(SimpleInventoryDto.builder().build()))
			.build()
		);
		assertThat(created.getId()).isEqualTo(1L);
	}
	
	@Test
	public void whenRebelEq1_thenUpdateLocationShouldBeUpdate() {
		RebelDto created = rebellionService.updateRebelLocation(1, RebelLocationDto.builder().build());
		assertThat(created.getId()).isEqualTo(1L);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void whenRebelNotRecorded_thenUpdateLocationShouldThrowException() {
		rebellionService.updateRebelLocation(300, RebelLocationDto.builder().build());
	}
	
	@Test
	public void whenValidReport_thenTraitorShouldBeReported() {
		RebelDto rebel = rebellionService.reportRebelTraitor(1, 2);
		assertThat(rebel.getId()).isEqualTo(1L);
	}
	
	@Test(expected = IllegalStateException.class)
	public void whenTraitorAndReportEquals_thenShouldThrowException() {
		rebellionService.reportRebelTraitor(1, 1);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void whenTraitorNotFound_thenShouldThrowException() {
		rebellionService.reportRebelTraitor(100, 1);
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void whenReporterNotFound_thenShouldThrowException() {
		rebellionService.reportRebelTraitor(1, 100);
	}
	
	@Test(expected = IllegalStateException.class)
	public void whenReporterIsTraitor_thenShouldThrowException() {
		rebellionService.reportRebelTraitor(1, 4);
	}
	
	@Test(expected = IllegalStateException.class)
	public void whenReporterAlreadyReport_thenShouldThrowException() {
		rebellionService.reportRebelTraitor(1, 3);
	}

	@Test
	public void whenRebelReceive3Report_thenShouldUpdateAsTraitor() {
		var rebel = rebellionService.reportRebelTraitor(2, 1);
		assertThat(rebel.isTraitor()).isEqualTo(true);
	}
	
	@Test
	public void whenNeedReport_thenShouldGetSituationReport() {
		var report = rebellionService.reportSituation();
		assertThat(report.getCountRebels()).isEqualTo(1L);
		assertThat(report.getCountTraitors()).isEqualTo(2L);
	}

}
