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
    public String index(Model model,
                        @RequestParam(value = "start", defaultValue = "1") Integer start,
                        @RequestParam(value = "size", defaultValue = "5") Integer size,
                        @RequestParam(value = "search", required = false) String search
    ) {
        List<QuestionDTO> questionDTOS = qusetionservice.listById(model, start, size,search);
        model.addAttribute("questionDTOS", questionDTOS);
        model.addAttribute("search", search);
        return "index";
    }
}
