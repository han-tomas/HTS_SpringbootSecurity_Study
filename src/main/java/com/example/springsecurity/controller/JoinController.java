package com.example.springsecurity.controller;

import com.example.springsecurity.dto.JoinDTO;
import com.example.springsecurity.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JoinController {
    private final JoinService joinService;
    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }
    @GetMapping("/join")
    public String joinP(){
        return "join";
    }
    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){

        joinService.joinProcess(joinDTO);
        return "redirect:/login";
    }
}
