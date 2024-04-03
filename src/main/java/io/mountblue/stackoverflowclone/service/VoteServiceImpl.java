package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.*;
import io.mountblue.stackoverflowclone.repository.AnswerRepository;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
import io.mountblue.stackoverflowclone.repository.UserRepository;
import io.mountblue.stackoverflowclone.repository.VoteRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteServiceImpl implements  VoteService {
    private final VoteRepository voteRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    private final AnswerRepository answerRepository;

    public VoteServiceImpl(VoteRepository voteRepository, QuestionRepository questionRepository, UserRepository userRepository, AnswerRepository answerRepository) {

        this.voteRepository = voteRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public void upVoteQuestion(Long questionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();
        Question question = questionRepository.findById(questionId).get();
        Vote existingVote = voteRepository.findByQuestionAndUser(question, user);
        if (existingVote != null) {
            if (existingVote.getVoteType() == VoteType.UPVOTE) {
                voteRepository.delete(existingVote);
                question.setVoteCount(question.getVoteCount() - 1);
            } else {
                existingVote.setVoteType(VoteType.UPVOTE);
                voteRepository.save(existingVote);
                question.setVoteCount(question.getVoteCount() + 2); // Increment by 2 for toggling
            }
        } else {
            Vote vote = new Vote();
            vote.setQuestion(question);
            vote.setUser(user);
            vote.setVoteType(VoteType.UPVOTE);
            voteRepository.save(vote);
            question.setVoteCount(question.getVoteCount() + 1);
        }
        questionRepository.save(question);
    }

    @Override
    public void downVoteQuestion(Long questionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();
        Question question = questionRepository.findById(questionId).get();
        Vote existingVote = voteRepository.findByQuestionAndUser(question, user);
        if (existingVote != null) {
            if (existingVote.getVoteType() == VoteType.DOWNVOTE) {
                voteRepository.delete(existingVote);
                question.setVoteCount(question.getVoteCount() + 1);
            } else {
                existingVote.setVoteType(VoteType.DOWNVOTE);
                voteRepository.save(existingVote);
                question.setVoteCount(question.getVoteCount() - 2); // Decrement by 2 for toggling
            }
        } else {
            Vote vote = new Vote();
            vote.setQuestion(question);
            vote.setUser(user);
            vote.setVoteType(VoteType.DOWNVOTE);
            voteRepository.save(vote);
            question.setVoteCount(question.getVoteCount() - 1);
        }
        questionRepository.save(question);
    }

    @Override
    public void upVoteAnswer(Long answerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();
        Answer answer = answerRepository.findById(answerId).get();
        Vote existingVote = voteRepository.findByAnswerAndUser(answer, user);
        if (existingVote != null) {
            if (existingVote.getVoteType() == VoteType.DOWNVOTE) {
                existingVote.setVoteType(VoteType.UPVOTE);
                voteRepository.save(existingVote);
                answer.setVoteCount(answer.getVoteCount() + 2);
            } else {
                voteRepository.delete(existingVote);
                answer.setVoteCount(answer.getVoteCount() - 1);
            }
        } else {
            Vote vote = new Vote();
            vote.setAnswer(answer);
            vote.setUser(user);
            vote.setVoteType(VoteType.UPVOTE);
            voteRepository.save(vote);
            answer.setVoteCount(answer.getVoteCount() + 1);
        }
        answerRepository.save(answer);
    }

    @Override
    public void downVoteAnswer(Long answerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName()).get();
        Answer answer = answerRepository.findById(answerId).get();
        Vote existingVote = voteRepository.findByAnswerAndUser(answer, user);
        if (existingVote != null) {
            if (existingVote.getVoteType() == VoteType.UPVOTE) {
                existingVote.setVoteType(VoteType.DOWNVOTE);
                voteRepository.save(existingVote);
                answer.setVoteCount(answer.getVoteCount() - 2);
            } else {
                voteRepository.delete(existingVote);
                answer.setVoteCount(answer.getVoteCount() + 1);
            }
        } else {
            Vote vote = new Vote();
            vote.setAnswer(answer);
            vote.setUser(user);
            vote.setVoteType(VoteType.DOWNVOTE);
            voteRepository.save(vote);
            answer.setVoteCount(answer.getVoteCount() - 1);
        }
        answerRepository.save(answer);
    }
}
