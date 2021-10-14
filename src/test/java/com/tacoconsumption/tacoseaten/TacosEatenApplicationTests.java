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
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
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
		assertEquals("Team1", savedTaco.getTeamAssociation());
	}

	@Test
	void getTacoCounts(){
		//Arrange
		eatTacosService.ateTaco("Team5");
		eatTacosService.ateTaco("Team1");
		eatTacosService.ateTaco("Team3");
		eatTacosService.ateTaco("Team2");
		eatTacosService.ateTaco("Team1");
		eatTacosService.ateTaco("Team2");
		eatTacosService.ateTaco("Team5");
		//Act
		TacoTeamCountList teamCounts = eatTacosService.retrieveCounts();
		//Assert
		assertNotNull(teamCounts);
		assertEquals(4, teamCounts.getTacoTeamCounts().size());
	}

	@Test
	void addComment() {
		//Act
		TacoComment saveComment = eatTacosService.makeComment("Taco Bell dont count");
		//Assert
		assertNotNull(saveComment);
		assertEquals("Taco Bell dont count", saveComment.getComment());
	}

	@Test
	void getComments() {
		//Arrange
		for (int i = 0; i < 25; i++) {
			eatTacosService.makeComment("Comment" + i);
		}
		//Act
		TacoCommentsList tacoComments = eatTacosService.retrieveComments();
		//Assert

		assertNotNull(tacoComments);
		assertFalse(tacoComments.isEmpty());
		assertEquals(20, tacoComments.getTacoCommentList().size());
	}

}
