package io.mountblue.stackoverflowclone.service;


public interface VoteService {
    public void upVoteQuestion(Long questionId);
    public void downVoteQuestion(Long questionId);
    public void upVoteAnswer(Long answerId);
    public void downVoteAnswer(Long answerId);

}
