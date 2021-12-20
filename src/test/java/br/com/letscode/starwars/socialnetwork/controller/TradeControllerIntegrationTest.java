package br.com.letscode.starwars.socialnetwork.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import br.com.letscode.starwars.socialnetwork.dto.SimpleInventoryDto;
import br.com.letscode.starwars.socialnetwork.dto.TradeDto;
import br.com.letscode.starwars.socialnetwork.dto.TradeResultDto;
import br.com.letscode.starwars.socialnetwork.service.TradingService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TradeController.class)
public class TradeControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TradingService service;

	@Test
	public void whenRequestTrade_thenReturnTradeResultDto() throws Exception {
		var tradeDataSuccess = TradeDto
		.builder()
		.whoOffer(1)
		.whoAccept(2)
		.offerItems(
			Arrays.asList(
				SimpleInventoryDto
				.builder()
				.itemID(1)
				.stock(1)
				.build()
			)
		)
		.acceptItems(
			Arrays.asList(
				SimpleInventoryDto
				.builder()
				.itemID(2)
				.stock(2)
				.build()
			)
		)
		.build();
		
		var tradeDataFailed = TradeDto
		.builder()
		.whoOffer(2)
		.whoAccept(3)
		.offerItems(
			Arrays.asList(
				SimpleInventoryDto
				.builder()
				.itemID(1)
				.stock(1)
				.build()
			)
		)
		.acceptItems(
			Arrays.asList(
				SimpleInventoryDto
				.builder()
				.itemID(2)
				.stock(2)
				.build()
			)
		)
		.build();
		
		given(service.trade(Mockito.eq(tradeDataSuccess))).willReturn(TradeResultDto.builder().complete(true).message("Mock Trading Success").build());
		given(service.trade(Mockito.eq(tradeDataFailed))).willReturn(TradeResultDto.builder().complete(false).message("Mock Trading Failed").build());

		mvc.perform(
			post("/trade")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(tradeDataSuccess))
		)
		.andExpect(
			status().isAccepted()
		).andExpectAll(
			jsonPath("$.complete", is(true)),
			jsonPath("$.message", is("Mock Trading Success"))
		);
		
		mvc.perform(
			post("/trade")
			.contentType(MediaType.APPLICATION_JSON)
			.content(JsonUtil.toJson(tradeDataFailed))
		)
		.andExpect(
			status().isBadRequest()
		).andExpectAll(
			jsonPath("$.complete", is(false)),
			jsonPath("$.message", is("Mock Trading Failed"))
		);

		verify(service, VerificationModeFactory.times(1)).trade(Mockito.eq(tradeDataSuccess));
		verify(service, VerificationModeFactory.times(1)).trade(Mockito.eq(tradeDataFailed));
		reset(service);
	}

}
