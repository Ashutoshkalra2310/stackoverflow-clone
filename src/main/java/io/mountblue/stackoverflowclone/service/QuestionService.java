package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionService {

    List<Question> getAllQuestions();
    void updateQuestion(Question question, MultipartFile file, String tagList);
    void deleteQuestion(Long id);
    Question findById(Long id);
    void save(Question question, MultipartFile file, String tags);
    void save(Question question);
    List<Question> filterQuestion(boolean noAnswer, boolean noAcceptedAnswer, String sortBy, String tagSearch);
    List<Question> search(String keyword);
    List<Question> findAll();
//    List<Question> searchTags(String keyword);
}
