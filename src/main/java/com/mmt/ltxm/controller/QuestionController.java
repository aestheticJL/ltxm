package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.QuestionDTO;
import com.mmt.ltxm.service.Qusetionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private Qusetionservice qusetionservice;
    @GetMapping("/question/{id}")
    public String Question(@PathVariable("id") Integer id, Model model){
        QuestionDTO questionDTO = qusetionservice.getById(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
