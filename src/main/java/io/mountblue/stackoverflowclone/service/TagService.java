package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();
    List<Question> findQuestionByTagName(Long id);
}
