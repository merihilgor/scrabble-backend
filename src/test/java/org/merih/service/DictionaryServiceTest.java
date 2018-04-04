/**
 * 
 */
package org.merih.service;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.merih.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Merih
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DictionaryServiceTest {

 
	@Test
	public void isAWordTest() {
		assertThat(DictionaryService.isAWord("makarna")).isEqualTo(true);
		assertThat(DictionaryService.isAWord("sfasdfasfd")).isEqualTo(false);
 
	}

	@Test
	public void calculatePointsTest() {
		assertThat(DictionaryService.calculatePoints("KaBaK")).isEqualTo(7);
		assertThat(DictionaryService.calculatePoints("kabak")).isEqualTo(7);
		assertThat(DictionaryService.calculatePoints("Je")).isEqualTo(11);
	}
}
