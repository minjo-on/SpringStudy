package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//@Controller
public class PathVariableController {
    @GetMapping("show")
    public String showView(){
        return "show";
    }

    @GetMapping("/function/{no}")
    public String selectFunction(@PathVariable Integer no){
        String view = null;
        switch (no){
            case 1:
                view = "function1";
                break;
            case 2:
                view = "function2";
                break;
            case 3:
                view = "function3";
                break;
        }
        return view;
    }

    @PostMapping(value = "send", params = "a")
    public String showAView(){
        return "a";
    }

    @PostMapping(value = "send", params = "b")
    public String showBView(){
        return "b";
    }

    @PostMapping(value = "send", params = "c")
    public String showCView(){
        return "c";
    }

}
