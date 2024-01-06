package com.example.demo.controller;

import com.example.demo.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
public class ThymeleafController {
    @GetMapping("show")
    public String showView(Model model){

        Member member = new Member(1,"회원01");

        model.addAttribute("name","이순신");
        model.addAttribute("mb",member);

        Member member1 = new Member(10,"홍길동");
        Member member2 = new Member(20,"이영희");

        List<String> directionList = new ArrayList<String>();
        directionList.add("동");
        directionList.add("서");
        directionList.add("남");
        directionList.add("북");


        Map<String,Member> memberMap = new HashMap<>();
        memberMap.put("hong",member1);
        memberMap.put("lee",member2);

        //반복문 실행을 위해
        List<Member> memberList = new ArrayList<>();
        memberList.add(member);
        memberList.add(member1);
        memberList.add(member2);

        model.addAttribute("list",directionList);
        model.addAttribute("map",memberMap);
        model.addAttribute("members",memberList);

        return "useThymeleaf";
    }

    @GetMapping("a")
    public String showA(){
        return "pageA";
    }
}
