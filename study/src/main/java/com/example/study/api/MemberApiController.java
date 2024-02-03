package com.example.study.api;


import com.example.study.dto.member.create.CreateMemberRequest;
import com.example.study.dto.member.create.CreateMemberResponse;
import com.example.study.dto.member.read.ReadMemberList;
import com.example.study.dto.member.read.ReadMemberResult;
import com.example.study.dto.member.update.UpdateMemberResponse;
import com.example.study.entity.Member;
import com.example.study.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /*
    등록 API
    */
    @PostMapping("/api/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid
                                             CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /*
    수정 API
    */
    @PutMapping("/api/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id,
                                             @RequestBody @Valid CreateMemberRequest request){
        memberService.update(id,request.getName());
        Member finder = memberService.findOne(id);
        return new UpdateMemberResponse(finder.getId(), finder.getName());
    }

    /*
    * 조회 API
    * */
    @GetMapping("api/members")
    public ReadMemberResult members(){
        List<Member> findMembers = memberService.findMembers();
        List<ReadMemberList> collect = findMembers.stream()
                .map(m -> new ReadMemberList(m.getName()))
                .collect(Collectors.toList());

        return new ReadMemberResult(collect, collect.size());
    }

}
