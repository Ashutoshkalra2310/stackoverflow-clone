package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements  VoteService{
    private final VoteRepository voteRepository;

    public VoteServiceImpl(VoteRepository voteRepository){
        this.voteRepository=voteRepository;
    }

    public void upVoteQuestion(Long questionId,Long userId) {
        voteRepository.upVoteQuestion(questionId,userId);
    }

    public void downVoteQuestion(Long questionId, Long userId) {
        voteRepository.downVoteQuestion(questionId,userId);

    }

    public void upVoteAnswer(Long answerId, Long userId) {
        upVoteAnswer(answerId,userId);
    }

    public void downVoteAnswer(Long answerId, Long userId) {
        downVoteAnswer(answerId,userId);
    }

}
