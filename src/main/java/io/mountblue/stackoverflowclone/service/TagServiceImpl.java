package io.mountblue.stackoverflowclone.service;

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
}
