package com.tacoconsumption.tacoseaten;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tacoconsumption.tacoseaten.Entities.TacoComment;
import com.tacoconsumption.tacoseaten.Entities.TacoEaten;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TacosEatenController.class)
public class TacosEatenControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TacosEatenService eatTacosService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void addTacoEaten() throws Exception {
        //Arrange
        TacoEaten newAteTaco = new TacoEaten("teamACT");

        //Act
        when(eatTacosService.ateTaco(anyString())).thenReturn(newAteTaco);
        mockMvc.perform(post("/api/atetaco")
                .contentType(MediaType.TEXT_PLAIN)
                .content("teamACT"))
        //Assert
        .andExpect(status().isOk())
        .andExpect(jsonPath("teamAssociation").value(newAteTaco.getTeamAssociation()));
    }

    @Test
    void getTacoNumbers() throws Exception {
        //Arrange
        List<TacoCounts> tacoTeamCounts = new ArrayList<>();
        tacoTeamCounts.add(new TacoCounts("One", 12));
        tacoTeamCounts.add(new TacoCounts("Two", 10));
        //Act
        when(eatTacosService.retrieveCounts()).thenReturn(new TacoTeamCountList(tacoTeamCounts));
        mockMvc.perform(get("/api/atetaco"))

                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tacoTeamCounts", hasSize(2)));
    }

    @Test
    void addTacoComment() throws Exception {
        //Arrange
        String newComment ="Fish tacos rule!";
        TacoComment commentMade = new TacoComment(newComment);

        //Act
        when(eatTacosService.makeComment(anyString())).thenReturn(commentMade);
        mockMvc.perform(post("/api/tacotalk")
                .contentType(MediaType.TEXT_PLAIN)
                .content(newComment))
                //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("comment").value(commentMade.getComment()));
    }

    @Test
    void retrieveTacoComments() throws Exception {
        //Arrange
        List<TacoComment> tacoComments = new ArrayList<>();
        tacoComments.add(new TacoComment("Fish tacos rule!"));
        tacoComments.add(new TacoComment("Taco mania!  8 tacos today!"));

        //Act
        when(eatTacosService.retrieveComments()).thenReturn(new TacoCommentsList(tacoComments));
        mockMvc.perform(get("/api/tacotalk"))

        //Assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tacoCommentList", hasSize(2)));

    }

}
