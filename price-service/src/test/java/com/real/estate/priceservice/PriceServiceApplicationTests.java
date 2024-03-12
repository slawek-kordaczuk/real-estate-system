package com.real.estate.priceservice;

import com.real.estate.priceservice.domain.dto.average.AveragePriceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void shouldReturnAveragePrice() {
		//Given & When
		AveragePriceResponse response = webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/api/real-estates-stats/{regionCode}")
						.queryParam("rooms", "4")
						.queryParam("size", "M")
						.queryParam("types", "flat")
						.queryParam("dateSince", "20231228")
						.queryParam("dateUntil", "20231231")
						.queryParam("page", "0")
						.queryParam("pageSize", "2")
						.build("SL_KATO"))

				.exchange()
				.expectStatus()
				.isEqualTo(OK)
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody(AveragePriceResponse.class)
				.returnResult()
				.getResponseBody();

		//Then
		assertEquals( "272616.49", response.getAvgValue());
	}

}
