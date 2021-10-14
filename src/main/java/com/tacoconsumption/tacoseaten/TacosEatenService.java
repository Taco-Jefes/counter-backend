package com.tacoconsumption.tacoseaten;

import com.tacoconsumption.tacoseaten.Entities.TacoComment;
import com.tacoconsumption.tacoseaten.Entities.TacoEaten;
import com.tacoconsumption.tacoseaten.Repositories.TacoCommentsRepository;
import com.tacoconsumption.tacoseaten.Repositories.TacosEatenRepository;
import org.springframework.stereotype.Service;

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
        TacoComment tacoComment = new TacoComment(comment);
        return tacoCommentsRepository.save(tacoComment);
    }

    public TacoCommentsList retrieveComments() {
        return new TacoCommentsList(tacoCommentsRepository.findFirst20ByOrderByCommentTimeStampDesc());
    }

    public TacoTeamCountList retrieveCounts() {
        TacosEatenList tacosEatenList = new TacosEatenList(tacosEatenRepository.findAllByOrderByTeamAssociation());

        TacoTeamCountList teamCounts = new TacoTeamCountList();

        String currentTeam = "";
        int teamCount = 0;

        int i = 0;
        int numTacosEaten = tacosEatenList.getTacosEaten().size();
        while (i < numTacosEaten) {
            currentTeam = tacosEatenList.getTacosEaten().get(i).getTeamAssociation();

            while (i < numTacosEaten && currentTeam.equals(tacosEatenList.getTacosEaten().get(i).getTeamAssociation())){
                teamCount++;
                i++;
            }
            teamCounts.getTacoTeamCounts().add(new TacoCounts(currentTeam,teamCount));
            teamCount = 0;

        }

        return teamCounts;
    }
}
