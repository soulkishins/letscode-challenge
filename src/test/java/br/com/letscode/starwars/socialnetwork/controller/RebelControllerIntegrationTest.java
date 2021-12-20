package br.com.letscode.starwars.socialnetwork.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.letscode.starwars.socialnetwork.JsonUtil;
import br.com.letscode.starwars.socialnetwork.dto.CreateRebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelDto;
import br.com.letscode.starwars.socialnetwork.dto.RebelLocationDto;
import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.TraitorReportDto;
import br.com.letscode.starwars.socialnetwork.enums.GenderEnum;
import br.com.letscode.starwars.socialnetwork.service.RebellionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RebelController.class)
public class RebelControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private RebellionService service;

	@Test
	public void whenCreateRebelDto_thenCreateRebel() throws Exception {
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
		
		var testRebel = RebelDto.builder().id(1L).build();

		given(service.createRebel(Mockito.eq(testCreateRebel))).willReturn(testRebel);

		mvc.perform(
			post("/rebels")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(testCreateRebel))
		)
		.andExpect(
			status().isCreated()
		).andExpect(
			jsonPath("$.id", is(1))
		);

		verify(service, VerificationModeFactory.times(1)).createRebel(Mockito.eq(testCreateRebel));
		reset(service);
	}
	
	@Test
	public void whenRebelLocationDto_thenUpdateRebelLocation() throws Exception {
		var location = RebelLocationDto
		.builder()
		.latitude(10)
		.longitude(20)
		.baseName("Base Rebel")
		.build();		
		var testRebel = RebelDto.builder().id(1L).build();

		given(service.updateRebelLocation(Mockito.eq(1L), Mockito.eq(location))).willReturn(testRebel);

		mvc.perform(
			patch("/rebels/1/location")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(location))
		)
		.andExpect(
			status().isOk()
		).andExpect(
			jsonPath("$.id", is(1))
		);

		verify(service, VerificationModeFactory.times(1)).updateRebelLocation(Mockito.eq(1L), Mockito.eq(location));
		reset(service);
	}
	
	@Test
	public void whenTraitorReportDto_thenReportRebelTraitor() throws Exception {
		var reportDetail = TraitorReportDto.builder().reporter(2).build();
		var testRebel = RebelDto.builder().id(1L).build();

		given(service.reportRebelTraitor(Mockito.eq(1l), Mockito.eq(2l))).willReturn(testRebel);

		mvc.perform(
			put("/rebels/1/traitor-report")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(reportDetail))
		)
		.andExpect(
			status().isOk()
		).andExpect(
			jsonPath("$.id", is(1))
		);

		verify(service, VerificationModeFactory.times(1)).reportRebelTraitor(Mockito.eq(1l), Mockito.eq(2l));
		reset(service);
	}

}
