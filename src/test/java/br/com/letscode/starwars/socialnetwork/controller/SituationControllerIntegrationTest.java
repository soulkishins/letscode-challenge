package br.com.letscode.starwars.socialnetwork.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.letscode.starwars.socialnetwork.dto.RebellionSituationDto;
import br.com.letscode.starwars.socialnetwork.dto.ReportInventoryDto;
import br.com.letscode.starwars.socialnetwork.service.RebellionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SituationController.class)
public class SituationControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private RebellionService service;

	@Test
	public void whenRequestReportSituation_thenReturnRebellionSituationDto() throws Exception {
		var reportData = RebellionSituationDto
		.builder()
		.countRebels(1)
		.countTraitors(2)
		.averageRebelInventory(
			Arrays.asList(
				ReportInventoryDto
				.builder()
				.itemID(1)
				.itemName("Item Test")
				.stock(10)
				.points(100)
				.build()
			)
		)
		.lostRebelInventory(
			Arrays.asList(
				ReportInventoryDto
				.builder()
				.itemID(2)
				.itemName("Item Test")
				.stock(20)
				.points(200)
				.build()
			)
		)
		.totalPointsLost(200)
		.build();

		given(service.reportSituation()).willReturn(reportData);

		mvc.perform(
			get("/situation")
			.contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(
			status().isOk()
		).andExpectAll(
			jsonPath("$.countRebels", is(1)),
			jsonPath("$.countTraitors", is(2)),
			jsonPath("$.averageRebelInventory[0].itemID", is(1)),
			jsonPath("$.averageRebelInventory[0].itemName", is("Item Test")),
			jsonPath("$.averageRebelInventory[0].stock", is(10)),
			jsonPath("$.averageRebelInventory[0].points", is(100)),
			jsonPath("$.lostRebelInventory[0].itemID", is(2)),
			jsonPath("$.lostRebelInventory[0].itemName", is("Item Test")),
			jsonPath("$.lostRebelInventory[0].stock", is(20)),
			jsonPath("$.lostRebelInventory[0].points", is(200)),
			jsonPath("$.totalPointsLost", is(200))
		);

		verify(service, VerificationModeFactory.times(1)).reportSituation();
		reset(service);
	}

}
