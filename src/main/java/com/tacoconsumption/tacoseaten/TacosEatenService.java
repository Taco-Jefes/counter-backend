package com.tacoconsumption.tacoseaten;

import com.tacoconsumption.tacoseaten.Entities.TacoComment;
import com.tacoconsumption.tacoseaten.Entities.TacoEaten;
import com.tacoconsumption.tacoseaten.Repositories.TacoCommentsRepository;
import com.tacoconsumption.tacoseaten.Repositories.TacosEatenRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TacosEatenService {
    private TacoCommentsRepository tacoCommentsRepository;
    private TacosEatenRepository tacosEatenRepository;

    public TacosEatenService(TacoCommentsRepository tacoCommentsRepository, TacosEatenRepository tacosEatenRepository) {
        this.tacosEatenRepository = tacosEatenRepository;
        this.tacoCommentsRepository = tacoCommentsRepository;
    }
    public TacoEaten ateTaco(String tacoTeam) {
        TacoEaten taco = new TacoEaten(tacoTeam);
        return tacosEatenRepository.save(taco);

    }

    public TacoComment makeComment(String comment) {
        TacoComment tacoComment = new TacoComment();
        tacoComment.setComment(comment);
        return tacoCommentsRepository.save(tacoComment);
    }

    public TacoCommentsList retrieveComments() {
        return new TacoCommentsList(tacoCommentsRepository.findFirst20ByOrderByCommentTimeStampDesc());
    }

    public TacoTeamCountList retrieveCounts() {
        TacosEatenList tacosEatenList = new TacosEatenList(tacosEatenRepository.findAll());

        List<TacoCounts> tacoTeamCounts = new ArrayList<>();
        tacoTeamCounts.add(new TacoCounts("One",12));
        tacoTeamCounts.add(new TacoCounts("Two", 10));

        return new TacoTeamCountList(tacoTeamCounts);
    }
}
