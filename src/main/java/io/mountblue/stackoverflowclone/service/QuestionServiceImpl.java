package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.*;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
import org.springframework.cglib.core.Local;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final TagService tagService;
    private final UserService userService;
    private final ViewService viewService;

    public QuestionServiceImpl(QuestionRepository questionRepository, TagService tagService, UserService userService, ViewService viewService) {
        this.questionRepository = questionRepository;
        this.tagService = tagService;
        this.userService = userService;
        this.viewService = viewService;
    }

    @Override
    public void save(Question question, String tagList) {
        String[] newTagNames = tagList.trim().split(",");
        List<Tag> existingTags = tagService.findAll();
        Map<String, Tag> allTagsByName = new HashMap<>();
        for (Tag tag : existingTags) {
            allTagsByName.put(tag.getName(), tag);
        }
        List<Tag> updatedTags = new ArrayList<>();
        for (String tagName : newTagNames) {
            Tag tag = allTagsByName.get(tagName.trim());
            if (tag == null) {
                tag = new Tag(tagName.trim());
            }
            updatedTags.add(tag);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        question.setTags(updatedTags);
        question.setUpdatedAt(formattedDateTime);
        question.setCreatedAt(formattedDateTime);
        question.setIsAnswered(Boolean.FALSE);
        question.setViewCount(0L);
        question.setVoteCount(0L);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        question.setUser(user);
        questionRepository.save(question);
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public List<Question> filterQuestion(boolean noAnswer, boolean noAcceptedAnswer, boolean newest, boolean oldest, boolean recentActivity, String tagSearch) {
        List<Question> filteredQuestions;
        if(!noAnswer && !noAcceptedAnswer && !newest && !oldest && !recentActivity && tagSearch.isEmpty()){
            filteredQuestions = questionRepository.findAll();
        } else {
            filteredQuestions = questionRepository.filterQuestions(noAnswer, noAcceptedAnswer, newest, oldest, recentActivity, tagSearch);
        }
        return filteredQuestions;
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public void updateQuestion(Question updatedQuestion, String tagList) {
        Question oldQuestion = questionRepository.findById(updatedQuestion.getId()).get();
        oldQuestion.setTitle(updatedQuestion.getTitle());
        oldQuestion.setContent(updatedQuestion.getContent());
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(dateTimeFormatter);
        oldQuestion.setUpdatedAt(formattedDateTime);
        String[] updatedTagNames = tagList.split(",");
        for (int i = 0; i < updatedTagNames.length; i++) {
            updatedTagNames[i] = updatedTagNames[i].trim();
        }
        List<Tag> existingTags = tagService.findAll();
        Map<String, Tag> existingTagsByName = new HashMap<>();
        for (Tag tag : existingTags) {
            existingTagsByName.put(tag.getName(), tag);
        }
        List<Tag> updatedTags = new ArrayList<>();
        for (String tagName : updatedTagNames) {
            Tag tag = existingTagsByName.get(tagName.trim());
            if (tag == null) {
                tag = new Tag(tagName.trim());
            }
            updatedTags.add(tag);
        }
        List<Tag> tagsToAdd = new ArrayList<>(updatedTags);
        List<Tag> tagsToRemove = new ArrayList<>(existingTags);
        tagsToRemove.removeAll(updatedTags);
        oldQuestion.getTags().addAll(tagsToAdd);
        oldQuestion.getTags().removeAll(tagsToRemove);
        oldQuestion.setTags(updatedTags);
        questionRepository.save(oldQuestion);
    }

    @Override
    public void deleteQuestion(Long id) {
        Question question = questionRepository.findById(id).get();
        questionRepository.delete(question);
    }

    @Override
    public Question findById(Long id) {
        Question question = questionRepository.findById(id).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());
        View view = viewService.findByUserAndQuestion(question, user);
        if(view == null){
            view = new View();
            view.setQuestion(question);
            view.setUser(user);
            viewService.save(view);
            question.setViewCount(question.getViewCount() + 1);
        }
        save(question);
        question = questionRepository.findById(id).get();
        return question;
    }

    public List<Question> search(String keyword, String username) {
        return questionRepository.search(keyword, username);
    }


}
