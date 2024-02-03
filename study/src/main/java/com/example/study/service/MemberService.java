package com.example.study.service;

import com.example.study.entity.Member;
import com.example.study.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
/*
    회원가입
*/
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
     정보수정
    */
    @Transactional
    public void update(Long id,String name){
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }

    public Member findOne(Long id){
        Member member = memberRepository.findOne(id);
        return member;
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
}
