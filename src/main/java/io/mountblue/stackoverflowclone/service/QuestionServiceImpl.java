package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.Tag;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
import org.springframework.cglib.core.Local;
import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final TagService tagService;

    public QuestionServiceImpl(QuestionRepository questionRepository, TagService tagService) {
        this.questionRepository = questionRepository;
        this.tagService = tagService;
    }

    @Override
    public void save(Question question, String tagList) {
        String[] newTagNames = tagList.trim().split(",");
        System.out.println(newTagNames);
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
                System.out.println(tag);
            }
            updatedTags.add(tag);
            System.out.println(tagName);
        }
        question.setTags(updatedTags);
        question.setUpdatedAt(LocalDateTime.now());
        question.setCreatedAt(LocalDateTime.now());
        question.setIsAnswered(Boolean.FALSE);
        question.setViewCount(0L);
        question.setVoteCount(0L);
        questionRepository.save(question);
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
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
        oldQuestion.setUpdatedAt(LocalDateTime.now());
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


}
