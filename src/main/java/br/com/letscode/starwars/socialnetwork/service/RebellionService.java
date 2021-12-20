package br.com.letscode.starwars.socialnetwork.service;

import java.util.Collections;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.dto.RebellionSituationDto;
import br.com.letscode.starwars.socialnetwork.mapper.InventoryMapper;
import br.com.letscode.starwars.socialnetwork.mapper.RebelLocationMapper;
import br.com.letscode.starwars.socialnetwork.mapper.RebelMapper;
import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.TraitorReport;
import br.com.letscode.starwars.socialnetwork.repository.ItemRepository;
import br.com.letscode.starwars.socialnetwork.repository.RebelRepository;
import br.com.letscode.starwars.socialnetwork.repository.TraitorReportRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RebellionService {
	
	private final static int TRAITOR_REPORT_BOUNDARY = 3;

	private final RebelMapper rebelMapper;
	private final RebelLocationMapper locationMapper;
	private final InventoryMapper inventoryMapper;
	private final RebelRepository rebelRepository;
	private final TraitorReportRepository traitorReportRepository;
	private final ItemRepository itemRepository;

	public RebelDto createRebel(CreateRebelDto rebel) {
		var record = rebelMapper.dtoToEntity(rebel);

		Objects
		.requireNonNullElse(record.getInventory(), Collections.<Inventory>emptyList())
		.forEach(e -> e.getId().setRebel(record));

		return rebelMapper.dtoFromEntity(rebelRepository.save(record));
	}

	public RebelDto updateRebelLocation(long rebelID, RebelLocationDto location) {
		
		var optRebel = rebelRepository.findById(rebelID);
		
		if (optRebel.isEmpty())
			throw new EntityNotFoundException("Rebel not found.");
		
		var rebel = optRebel.get();
		rebel.setLocation(locationMapper.dtoToEntity(location));
		
		return rebelMapper.dtoFromEntity(rebelRepository.save(rebel));
	}

	public RebelDto reportRebelTraitor(long traitor, long reporter) {
		
		if (traitor == reporter)
			throw new IllegalStateException("Reporter cannot report yourself.");

		var optTraitor = rebelRepository.findById(traitor);
		if (optTraitor.isEmpty())
			throw new EntityNotFoundException("Traitor not found.");

		var optReporter = rebelRepository.findById(reporter);
		if (optReporter.isEmpty())
			throw new EntityNotFoundException("Reporter not found.");
		
		if (optReporter.get().isTraitor())
			throw new IllegalStateException("Reporter is a Traitor.");

		if (traitorReportRepository.count(
				traitorReportRepository.findByReporter(reporter)
				.and(traitorReportRepository.findByTraitor(traitor))
			) > 0)
			throw new IllegalStateException("Reporter already report this Traitor.");

		traitorReportRepository.save(
			TraitorReport.builder().traitor(optTraitor.get()).reporter(optReporter.get()).build()
		);
		
		if (traitorReportRepository.count(traitorReportRepository.findByTraitor(traitor)) >= TRAITOR_REPORT_BOUNDARY) {
			optTraitor.get().setTraitor(true);
			rebelRepository.save(optTraitor.get());
		}

		return rebelMapper.dtoFromEntityWithoutIventory(optTraitor.get());
	}

	public RebellionSituationDto reportSituation() {
		
		var report = RebellionSituationDto.builder();
		
		var countRebels = rebelRepository.count(rebelRepository.findByIsNotTraitor());
		report.countRebels(countRebels);
		var countTraitors = rebelRepository.count(rebelRepository.findByIsTraitor());
		report.countTraitors(countTraitors);
		
		var rebellionInventory = itemRepository.listRebellionInventory();
		var avgRebellionInventory = inventoryMapper.listDtoFromListView(rebellionInventory);
		// Remember: Do avg after load sum from db, because db do avg between same item and ignore rebel with zero stock
		avgRebellionInventory.forEach(e -> {e.setStock(e.getStock() / (int) countRebels); e.setPoints(e.getPoints() / (int) countRebels);});
		report.averageRebelInventory(avgRebellionInventory);
		var rebellionLostInventory = itemRepository.listLostInventory();
		report.lostRebelInventory(inventoryMapper.listDtoFromListView(rebellionLostInventory));

		report.totalPointsLost(rebellionLostInventory.stream().mapToInt(e -> e.getPoints()).reduce((v1, v2) -> v1 + v2).orElse(0));

		return report.build();
	}

}
