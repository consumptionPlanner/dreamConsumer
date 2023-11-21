package com.dreamconsumer.consumptionplanner.tag.mapper;

import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import com.dreamconsumer.consumptionplanner.tag.domain.Tag;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    default Tag stringToTag(String tag, MemberItem memberItem) {
        if ( tag == null ) {
            return null;
        }

        Tag createdTag = new Tag();
        createdTag.setContents(tag);
        createdTag.setMemberItem(memberItem);
        return createdTag;
    }

    default List<Tag> stringsToTags(List<String> stringTags, MemberItem memberItem) {
        if (stringTags == null) {
            return null;
        }

        List<Tag> tagEntities = new ArrayList<>(stringTags.size());
        for (String tagContent : stringTags) {
            Tag tag = stringToTag(tagContent, memberItem);
            tag.setMemberItem(memberItem);
            tagEntities.add(tag);
        }

        return tagEntities;
    }
}
