package io.mountblue.stackoverflowclone.controller;

import org.springframework.stereotype.Controller;
import io.mountblue.stackoverflowclone.service.VoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService){
        this.voteService=voteService;
    }
    @GetMapping("/upvoteQuestion/{questionId}")
    public String upvoteQuestion(@PathVariable Long questionId) {
        voteService.upVoteQuestion(questionId);
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/downvoteQuestion/{questionId}")
    public String downvoteQuestion(@PathVariable Long questionId) {
        voteService.downVoteQuestion(questionId);
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/upvoteAnswer/{answerId}/{questionId}")
    public String upvoteAnswer(@PathVariable Long answerId, @PathVariable Long questionId) {
        voteService.upVoteAnswer(answerId);
        return "redirect:/question/" + questionId;
    }

    @GetMapping("/downvoteAnswer/{answerId}/{questionId}")
    public String downvoteAnswer(@PathVariable Long answerId, @PathVariable Long questionId) {
        voteService.downVoteAnswer(answerId);
        return "redirect:/question/" + questionId;
    }


}
