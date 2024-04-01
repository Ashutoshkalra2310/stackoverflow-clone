package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.Tag;
import io.mountblue.stackoverflowclone.repository.QuestionRepository;
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
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).get();
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
        String[] updatedTagNames = tagList.trim().split(",");
        List<Tag> existingTags = tagService.findAll();
        Map<String, Tag> existingTagsByName = new HashMap<>();
        for (Tag tag : existingTags) {
            existingTagsByName.put(tag.getName(), tag);
        }
        Set<Tag> updatedTags = new HashSet<>();
        for (String tagName : updatedTagNames) {
            Tag tag = existingTagsByName.get(tagName.trim());
            if (tag == null) {
                tag = new Tag(tagName.trim());
            }
            updatedTags.add(tag);
        }
        Set<Tag> tagsToAdd = new HashSet<>(updatedTags);
        Set<Tag> tagsToRemove = new HashSet<>(existingTags);
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
}
