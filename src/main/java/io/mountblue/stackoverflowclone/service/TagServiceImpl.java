package io.mountblue.stackoverflowclone.service;

import io.mountblue.stackoverflowclone.entity.Question;
import io.mountblue.stackoverflowclone.entity.Tag;
import io.mountblue.stackoverflowclone.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }


    @Override
    public List<Question> findQuestionByTagName(Long id) {
        Tag tag = tagRepository.findById(id).orElse(null);
        return tagRepository.findQuestionsByTagName(tag.getName());
    }
}
