package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.Tag;
import io.mountblue.stackoverflowclone.entity.User;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
import org.springframework.cglib.core.Local;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final TagService tagService;
    private final UserService userService;
    private final StorageService storageService;

    public QuestionServiceImpl(QuestionRepository questionRepository, TagService tagService, UserService userService, StorageService storageService) {
        this.questionRepository = questionRepository;
        this.tagService = tagService;
        this.userService = userService;
        this.storageService = storageService;
    }

    @Override
    public void save(Question question, MultipartFile file, String tagList) {
        question.setImageFileName(storageService.uploadFiles(file));
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
    public List<Question> filterQuestion(boolean noAnswer, boolean noAcceptedAnswer,String sortBy, String tagSearch) {
        List<Question> filteredQuestions;
        if(!noAnswer && !noAcceptedAnswer && sortBy.isEmpty() && tagSearch.isEmpty()){
            filteredQuestions = questionRepository.findAll();
        } else {
            filteredQuestions = questionRepository.filterQuestions(noAnswer, noAcceptedAnswer, sortBy, tagSearch);
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
        return questionRepository.findById(id).get();
    }

    @Override
    public List<Question> search(String keyword) {
        return questionRepository.search(keyword);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<Question> searchTags(String keyword) {
        List<Question> allQuestions = questionRepository.findAll();
        List<Question> searchResults=allQuestions.stream()
                .filter(Question -> Question.getTags().stream()
                        .anyMatch(Tag -> Tag.getName().equalsIgnoreCase(keyword)))
                .collect(Collectors.toList());
        return searchResults;
    }

}
