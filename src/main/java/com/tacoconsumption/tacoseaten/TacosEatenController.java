package com.tacoconsumption.tacoseaten;

import com.tacoconsumption.tacoseaten.Entities.TacoComment;
import com.tacoconsumption.tacoseaten.Entities.TacoEaten;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TacosEatenController {

    private TacosEatenService eatTacosService;

    public TacosEatenController(TacosEatenService eatTacosService) {
        this.eatTacosService = eatTacosService;
    }

    @PostMapping("/tacos")
    public TacoEaten recordTaco(@RequestBody String taco) {

        return eatTacosService.ateTaco(taco);
    }

    @GetMapping("/tacos")
    public ResponseEntity<TacoTeamCountList> retrieveCounts() {
        return ResponseEntity.ok(eatTacosService.retrieveCounts());
    }

    @PostMapping("/comments")
    public TacoComment recordComment(@RequestBody String comment) {

        return eatTacosService.makeComment(comment);
    }

    @GetMapping("/comments")
    public ResponseEntity<TacoCommentsList> retrieveComments() {
        return ResponseEntity.ok(eatTacosService.retrieveComments());
    }
}
