package com.tacoconsumption.tacoseaten;

import com.tacoconsumption.tacoseaten.Entities.TacoComment;
import com.tacoconsumption.tacoseaten.Entities.TacoEaten;
import com.tacoconsumption.tacoseaten.Repositories.TacoCommentsRepository;
import com.tacoconsumption.tacoseaten.Repositories.TacosEatenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TacosEatenServiceTests {

    private TacosEatenService eatTacosService;

    @Mock
    TacoCommentsRepository tacoCommentsRepository;

    @Mock
    TacosEatenRepository tacosEatenRepository;

    @BeforeEach
    void setup () {
        eatTacosService = new TacosEatenService(tacoCommentsRepository, tacosEatenRepository);
    }

    @Test
    void recordTacoEaten() {
        //Arrange
        String team = "teamACT";
        TacoEaten taco = new TacoEaten(team);
        //Act
        when(tacosEatenRepository.save(any(TacoEaten.class))).thenReturn(taco);
        TacoEaten savedTaco = eatTacosService.ateTaco(team);
        //Assert
        assertNotNull(savedTaco);
        assertEquals(taco.getTeamAssociation(), savedTaco.getTeamAssociation());
        verify(tacosEatenRepository).save(any(TacoEaten.class));
    }

    @Test
    void getTacoCounts() {
        //Arrange

        List<TacoEaten> tacosEaten = new ArrayList<>();
        tacosEaten.add(new TacoEaten("One"));
        tacosEaten.add(new TacoEaten("Two"));

        //Act
        when(tacosEatenRepository.findAll()).thenReturn(tacosEaten);
        TacoTeamCountList savedCounts = eatTacosService.retrieveCounts();

        //Assert
        assertNotNull(savedCounts);
        assertFalse(savedCounts.isEmpty());
        verify(tacosEatenRepository).findAll();

    }

    @Test
    void recordComment() {
        //Arrange
        String comment = "Tacos every night!";
        TacoComment commentMade = new TacoComment(comment);
        //Act
        when(tacoCommentsRepository.save(any(TacoComment.class))).thenReturn(commentMade);
        TacoComment savedComment = eatTacosService.makeComment(comment);
        //Assert
        assertNotNull(savedComment);
        assertEquals(commentMade.getComment(), savedComment.getComment());
        verify(tacoCommentsRepository).save(any(TacoComment.class));
    }

    @Test
    void retrieveComments() {
        //Arrange
        List<TacoComment> commentsMade = new ArrayList<>();
        commentsMade.add(new TacoComment("Fish tacos rule!"));
        commentsMade.add(new TacoComment("Fish tacos don't count."));

        //Act
        when(tacoCommentsRepository.findAll()).thenReturn(commentsMade);
        TacoCommentsList savedComments = eatTacosService.retrieveComments();

        //Assert
        assertNotNull(savedComments);
        assertFalse(savedComments.isEmpty());
        verify(tacoCommentsRepository).findAll();

    }
}
