package com.dreamconsumer.consumptionplanner.member.controller;


import com.dreamconsumer.consumptionplanner.member.domain.Member;
import com.dreamconsumer.consumptionplanner.member.dto.MemberPostDto;
import com.dreamconsumer.consumptionplanner.member.mapper.MemberMapper;
import com.dreamconsumer.consumptionplanner.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping("/sign-up")
    public ResponseEntity postUser(@RequestBody MemberPostDto memberPostDto) {
        Member member = mapper.MemberPostDtoToMember(memberPostDto);
        Member createdMember = memberService.createUser(member);
        return new ResponseEntity<>(mapper.MemberToMemberResponseDto(createdMember), HttpStatus.CREATED);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getUser(@PathVariable("member-id") @Min(1) long userId) {
        Member member = memberService.getUser(userId);
        return new ResponseEntity<>(mapper.MemberToMemberMyPageResponseDto(member), HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteUser(@PathVariable("member-id") @Min(1) long userId) {
        memberService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/activate/{member-id}") // 휴면 계정 활성화
    public ResponseEntity activateUser(@PathVariable("member-id") @Min(1) long userId) {
        memberService.activateUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
