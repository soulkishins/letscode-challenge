package br.com.letscode.starwars.socialnetwork.initialize;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import br.com.letscode.starwars.socialnetwork.model.Item;
import br.com.letscode.starwars.socialnetwork.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InitializeItemsTable {

	private final ItemRepository itemRepository;

	@PostConstruct
	public void initialize() {

		itemRepository.save(Item.builder().name("Arma").points((byte) 4).build());
		itemRepository.save(Item.builder().name("Munição").points((byte) 3).build());
		itemRepository.save(Item.builder().name("Água").points((byte) 2).build());
		itemRepository.save(Item.builder().name("Comida").points((byte) 1).build());

	}

}
