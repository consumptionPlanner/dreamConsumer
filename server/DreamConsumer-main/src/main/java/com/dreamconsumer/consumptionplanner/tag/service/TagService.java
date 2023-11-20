package com.dreamconsumer.consumptionplanner.tag.service;

import com.dreamconsumer.consumptionplanner.tag.repository.TagRepository;
import com.dreamconsumer.consumptionplanner.tag.domain.Tag;
import com.dreamconsumer.consumptionplanner.tag.mapper.TagMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> saveTags(List<Tag> tags) {
        return tagRepository.saveAll(tags);
    }
}
