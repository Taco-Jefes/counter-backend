package com.tacoconsumption.tacoseaten;

import com.tacoconsumption.tacoseaten.Entities.TacoComment;
import com.tacoconsumption.tacoseaten.Entities.TacoEaten;
import com.tacoconsumption.tacoseaten.Repositories.TacoCommentsRepository;
import com.tacoconsumption.tacoseaten.Repositories.TacosEatenRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TacosEatenApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Autowired
	TacosEatenRepository tacosEatenRepository;

	@Autowired
	TacoCommentsRepository tacoCommentsRepository;

	TacosEatenService eatTacosService;

	@BeforeEach
	void set() {
		eatTacosService = new TacosEatenService(tacoCommentsRepository, tacosEatenRepository);
	}

	@AfterEach
	void tearDown(){
		tacoCommentsRepository.deleteAll();
		tacosEatenRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void addTaco() {
		//Act
		TacoEaten savedTaco = eatTacosService.ateTaco("Team1");
		//Assert
		assertNotNull(savedTaco);
		assertEquals("team1", savedTaco.getTeamAssociation());
	}

	@Test
	void addComment() {
		//Act
		TacoComment saveComment = eatTacosService.makeComment("Taco Bell dont count");
		//Assert
		assertNotNull(saveComment);
		assertEquals("Taco Bell dont count", saveComment.getComment());
	}
}
