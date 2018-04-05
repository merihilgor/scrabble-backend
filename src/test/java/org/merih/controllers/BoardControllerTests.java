/**
 * 
 */
package org.merih.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.merih.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;



/**
 * @author Merih
 *
 */
 
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BoardControllerTests {

 
	@Autowired
	private WebTestClient webClient;

	
	@Autowired
	BoardService boardService;

	
	/*
	 * REF :
	 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
	 * 
	 */
	 
	@Test
	public void testCreateBoardRestSuccess() {

		String result = this.webClient.post().uri("/board/createboard").exchange().expectStatus().isOk()
	 	.expectBody(String.class).returnResult().getResponseBody();

		assertThat(Long.valueOf(result)).isGreaterThan(0);
	}
	 
	@Test
	public void testCreateBoardSuccess() {

		assertThat(boardService.addBoard()).isGreaterThan(0);

	}

	 
}
