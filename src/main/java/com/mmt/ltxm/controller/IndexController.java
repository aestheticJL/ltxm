package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.QuestionDTO;
import com.mmt.ltxm.service.Qusetionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private Qusetionservice qusetionservice;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest, Model model, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        List<QuestionDTO> questionDTOS = qusetionservice.listById(model, start, size);
        model.addAttribute("questionDTOS", questionDTOS);
        return "index";
    }
}
