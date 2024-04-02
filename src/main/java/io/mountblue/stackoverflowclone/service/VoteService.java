package io.mountblue.stackoverflowclone.service;


public interface VoteService {
    public void upVoteQuestion(Long questionId,Long userId);
    public void downVoteQuestion(Long questionId, Long userId);
    public void upVoteAnswer(Long answerId, Long userId);
    public void downVoteAnswer(Long answerId, Long userId);

}
