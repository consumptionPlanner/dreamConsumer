package com.example.demo.user_item.service;

import com.example.demo.item.dto.ItemRequestDto;
import com.example.demo.user_item.domain.UserItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserItemService {
    public UserItem saveUserItem(UserItem userItem) {
        return new UserItem();
    }
}
