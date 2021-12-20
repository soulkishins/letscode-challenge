package br.com.letscode.starwars.socialnetwork.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.TradeDto;
import br.com.letscode.starwars.socialnetwork.dto.TradeResultDto;
import br.com.letscode.starwars.socialnetwork.model.Inventory;
import br.com.letscode.starwars.socialnetwork.model.InventoryID;
import br.com.letscode.starwars.socialnetwork.model.Item;
import br.com.letscode.starwars.socialnetwork.model.Rebel;
import br.com.letscode.starwars.socialnetwork.repository.InventoryRepository;
import br.com.letscode.starwars.socialnetwork.repository.ItemRepository;
import br.com.letscode.starwars.socialnetwork.repository.RebelRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradingService {

	private final RebelRepository rebelRepository;
	private final InventoryRepository inventoryRepository;
	private final ItemRepository itemRepository;

	public TradeResultDto trade(TradeDto tradeData) {
		
		var optRebelWhoOffer = rebelRepository.findById(tradeData.getWhoOffer());
		if (optRebelWhoOffer.isEmpty())
			return TradeResultDto.builder().message("Rebel who offered not found.").build();
		
		if (optRebelWhoOffer.get().isTraitor())
			return TradeResultDto.builder().message("Rebel who offered is traitor.").build();

		var optRebelWhoAccept = rebelRepository.findById(tradeData.getWhoAccept());
		if (optRebelWhoAccept.isEmpty())
			return TradeResultDto.builder().message("Rebel who accepted not found.").build();

		if (optRebelWhoAccept.get().isTraitor())
			return TradeResultDto.builder().message("Rebel who accepted is traitor.").build();
		
		if (tradeData.getOfferItems().stream().filter(e -> e.getStock() < 1).count() != 0
				|| tradeData.getAcceptItems().stream().filter(e -> e.getStock() < 1).count() != 0)
			return TradeResultDto.builder().message("Illegal trade stock.").build();

		if (!hasEqualsTradePoints(tradeData.getOfferItems(), tradeData.getAcceptItems()))
			return TradeResultDto.builder().message("Illegal trade points.").build();

		var offeredItems = retrieveItemsUpdated(optRebelWhoOffer.get(), tradeData.getAcceptItems(), tradeData.getOfferItems());
		if (offeredItems.stream().filter(e -> e.getStock() < 0).count() != 0)
			return TradeResultDto.builder().message("Rebel who offered don`t have enough items.").build();

		var acceptItems = retrieveItemsUpdated(optRebelWhoAccept.get(), tradeData.getOfferItems(), tradeData.getAcceptItems());
		if (acceptItems.stream().filter(e -> e.getStock() < 0).count() != 0)
			return TradeResultDto.builder().message("Rebel who accepted don`t have enough items.").build();

		inventoryRepository.saveAll(offeredItems);
		inventoryRepository.saveAll(acceptItems);

		return TradeResultDto.builder().complete(true).message("Trade Complete.").build();
	}
	
	private List<Inventory> retrieveItemsUpdated(Rebel rebel, List<SimpleInventoryDto> increment, List<SimpleInventoryDto> decrement) {
		var inventory = inventoryRepository
		.findAll(
			inventoryRepository.findByRebel(rebel.getId())
		);
		increment.forEach(e -> updateStockOrIncludeNew(e.getItemID(), e.getStock(), inventory, rebel));
		decrement.forEach(e -> updateStockOrIncludeNew(e.getItemID(), -e.getStock(), inventory, rebel));
		return inventory;
	}
	
	private void updateStockOrIncludeNew(Integer itemID, int stock, List<Inventory> inventory, Rebel rebel) {
		var item = getInventory(itemID, inventory);
		if (Objects.isNull(item)) {
			item = Inventory
					.builder()
					.id(
						InventoryID
						.builder()
						.rebel(rebel)
						.item(
							Item
							.builder()
							.id(itemID)
							.build()
						)
						.build()
					)
					.build();
			inventory.add(item);
		}
		item.setStock(item.getStock() + stock);
	}
	
	private boolean hasEqualsTradePoints(List<SimpleInventoryDto> contents, List<SimpleInventoryDto> offers) {
		var items = itemRepository.findAll();

		var contentPoints = 0;
		for (var content : contents) {
			var item = getItem(content.getItemID(), items);
			contentPoints += item.getPoints() * content.getStock();
		}

		var offerPoints = 0;
		for (var offer : offers) {
			var item = getItem(offer.getItemID(), items);
			offerPoints += item.getPoints() * offer.getStock();
		}

		return contentPoints == offerPoints;
	}
	
	private Item getItem(Integer itemID, Iterable<Item> items) {
		var it = items.iterator();
		Item item = null;
		while (it.hasNext()) {
			item = it.next();
			if (item.getId().equals(itemID))
				break;
		}
		return item;
	}
	
	private Inventory getInventory(Integer itemID, Iterable<Inventory> inventory) {
		for (var item : inventory) {
			if (item.getId().getItem().getId().equals(itemID))
				return item;
		}
		return null;
	}

}
