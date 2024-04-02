package io.mountblue.stackoverflowclone.controller;

import io.mountblue.stackoverflowclone.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService){
        this.voteService=voteService;
    }
    @PostMapping("/upvoteQuestion/{questionId}")
    public String upvoteQuestion(@PathVariable Long questionId,@PathVariable Long userId) {
        voteService.upVoteQuestion(questionId, userId);
        return "redirect:show-question";
    }

    @PostMapping("/downvoteQuestion/{questionId}")
    public String downvoteQuestion(@PathVariable Long questionId,@PathVariable Long userId) {
        voteService.downVoteQuestion(questionId, userId);
        return "redirect:show-question";
    }

    @PostMapping("/upvoteAnswer/{answerId}")
    public String upvoteAnswer(@PathVariable Long answerId,@PathVariable Long userId) {
        voteService.upVoteAnswer(answerId, userId);
        return "redirect:show-question";
    }

    @PostMapping("/downvoteAnswer/{answerId}")
    public String downvoteAnswer(@PathVariable Long answerId,@PathVariable Long userId) {
        voteService.downVoteAnswer(answerId, userId);
        return "redirect:show-question";
    }


}
