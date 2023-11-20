package com.dreamconsumer.consumptionplanner.member_item.service;

import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberItemService {

    private final MemberItemRepository memberItemRepository;
    public MemberItem saveUserItem(MemberItem memberItem) {
        memberItemRepository.save(memberItem);
        return new MemberItem();
    }
}
