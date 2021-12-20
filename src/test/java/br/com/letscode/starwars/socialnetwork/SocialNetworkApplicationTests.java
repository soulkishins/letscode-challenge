package br.com.letscode.starwars.socialnetwork;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.TraitorReportDto;
import br.com.letscode.starwars.socialnetwork.enums.GenderEnum;
import br.com.letscode.starwars.socialnetwork.service.RebellionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SocialNetworkApplication.class)
@AutoConfigureMockMvc
class SocialNetworkApplicationTests {
	
	@Autowired
    private MockMvc mvc;

    @Autowired
	private RebellionService rebelService;
    
    @Test
	void testRepositoryLayer() throws Exception {
    	var testCreateRebel = CreateRebelDto
		.builder()
		.name("Rebel Test")
		.age(Short.valueOf("30"))
		.gender(GenderEnum.MALE)
		.location(
			RebelLocationDto
			.builder()
			.latitude(10)
			.longitude(20)
			.baseName("Base Rebel")
			.build()
		)
		.inventory(
			Arrays.asList(
				SimpleInventoryDto.builder().itemID(1).stock(10).build()
			)
		)
		.build();
		
		mvc.perform(
			post("/rebels")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(testCreateRebel))
		)
		.andExpect(
			status().isCreated()
		).andExpectAll(
			jsonPath("$.id", notNullValue()),
			jsonPath("$.name", is("Rebel Test"))
		);
		
		mvc.perform(
			post("/rebels")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(null))
		)
		.andExpect(
			status().isInternalServerError()
		);
		
		mvc.perform(
			patch("/rebels/1000/location")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(RebelLocationDto.builder().build()))
		)
		.andExpect(
			status().isNotFound()
		);
		
		mvc.perform(
			put("/rebels/1/traitor-report")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(TraitorReportDto.builder().reporter(1).build()))
		)
		.andExpect(
			status().isPreconditionFailed()
		);
    }

	@Test
	void testServiceLayer() {
		rebelService.createRebel(
			CreateRebelDto
			.builder()
			.name("Rebel1")
			.age(Short.valueOf("30"))
			.gender(GenderEnum.MALE)
			.location(
				RebelLocationDto
				.builder()
				.latitude(0)
				.longitude(0)
				.baseName("Base Rebel")
				.build()
			)
			.inventory(
				Arrays.asList(
					SimpleInventoryDto.builder().itemID(1).stock(10).build(),
					SimpleInventoryDto.builder().itemID(2).stock(10).build()
				)
			)
			.build()
		);
		
		rebelService.createRebel(
			CreateRebelDto
			.builder()
			.name("Rebel2")
			.age(Short.valueOf("30"))
			.gender(GenderEnum.FEMALE)
			.location(
				RebelLocationDto
				.builder()
				.latitude(0)
				.longitude(0)
				.baseName("Base Rebel")
				.build()
			)
			.inventory(
				Arrays.asList(
					SimpleInventoryDto.builder().itemID(3).stock(10).build(),
					SimpleInventoryDto.builder().itemID(4).stock(10).build()
				)
			)
			.build()
		);
		
		rebelService.createRebel(
			CreateRebelDto
			.builder()
			.name("Rebel3")
			.age(Short.valueOf("30"))
			.gender(GenderEnum.FEMALE)
			.location(
				RebelLocationDto
				.builder()
				.latitude(0)
				.longitude(0)
				.baseName("Base Rebel")
				.build()
			)
			.inventory(
				Arrays.asList(
					SimpleInventoryDto.builder().itemID(1).stock(10).build(),
					SimpleInventoryDto.builder().itemID(2).stock(10).build(),
					SimpleInventoryDto.builder().itemID(3).stock(10).build(),
					SimpleInventoryDto.builder().itemID(4).stock(10).build()
				)
			)
			.build()
		);
		
		rebelService.createRebel(
			CreateRebelDto
			.builder()
			.name("Rebel4")
			.age(Short.valueOf("30"))
			.gender(GenderEnum.MALE)
			.location(
				RebelLocationDto
				.builder()
				.latitude(0)
				.longitude(0)
				.baseName("Base Rebel")
				.build()
			)
			.inventory(
				Arrays.asList(
					SimpleInventoryDto.builder().itemID(1).stock(10).build(),
					SimpleInventoryDto.builder().itemID(2).stock(10).build(),
					SimpleInventoryDto.builder().itemID(3).stock(10).build(),
					SimpleInventoryDto.builder().itemID(4).stock(10).build()
				)
			)
			.build()
		);

		rebelService.reportRebelTraitor(2, 1);
		rebelService.reportRebelTraitor(2, 3);
		rebelService.reportRebelTraitor(2, 4);
	}

	@Test
	void testControllerLayer() {
    	
    }
}
