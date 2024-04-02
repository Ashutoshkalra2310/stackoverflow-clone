package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.*;
import io.mountblue.stackoverflowclone.repository.AnswerRepository;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
import io.mountblue.stackoverflowclone.repository.UserRepository;
import io.mountblue.stackoverflowclone.repository.VoteRepository;
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

    public void upVoteQuestion(Long questionId, Long userId) {
        Optional<Vote> existingVote = voteRepository.findByQuestionIdAndUserId(questionId, userId);
        if (existingVote.isEmpty()) {
            Optional<Question> questionOptional = questionRepository.findById(questionId);
            Optional<User> userOptional = userRepository.findById(userId);
            Question question = questionOptional.get();
            Vote vote = new Vote();
            vote.setQuestion(question);
            vote.setUser(userOptional.get());
            vote.setVoteType(VoteType.UPVOTE);
            vote.setAnswer(null);
            voteRepository.save(vote);

        }
    }

    public void downVoteQuestion(Long questionId, Long userId) {
        Optional<Vote> existingVote = voteRepository.findByQuestionIdAndUserId(questionId, userId);
        if (existingVote.isEmpty()) {
            Optional<Question> questionOptional = questionRepository.findById(questionId);
            Optional<User> userOptional = userRepository.findById(userId);
            Question question = questionOptional.get();
            Vote vote = new Vote();
            vote.setQuestion(question);
            vote.setUser(userOptional.get());
            vote.setVoteType(VoteType.DOWNVOTE);
            vote.setAnswer(null);
            voteRepository.save(vote);

        }

    }

    public void upVoteAnswer(Long answerId, Long userId) {
        Optional<Vote> existingVote = voteRepository.findByAnswerIdAndUserId(answerId, userId);
        if (existingVote.isEmpty()) {
            Optional<Answer> answerOptional = answerRepository.findById(answerId);
            Optional<User> userOptional = userRepository.findById(userId);
            Answer answer = answerOptional.get();
            Vote vote = new Vote();
            vote.setAnswer(answer);
            vote.setUser(userOptional.get());
            vote.setVoteType(VoteType.UPVOTE);
            vote.setQuestion(null);
            voteRepository.save(vote);

        }
    }

    public void downVoteAnswer(Long answerId, Long userId) {

        Optional<Vote> existingVote = voteRepository.findByAnswerIdAndUserId(answerId, userId);
        if (existingVote.isEmpty()) {
            Optional<Answer> answerOptional = answerRepository.findById(answerId);
            Optional<User> userOptional = userRepository.findById(userId);
            Answer answer = answerOptional.get();
            Vote vote = new Vote();
            vote.setAnswer(answer);
            vote.setUser(userOptional.get());
            vote.setVoteType(VoteType.DOWNVOTE);
            vote.setQuestion(null);
            voteRepository.save(vote);
        }

    }
}
