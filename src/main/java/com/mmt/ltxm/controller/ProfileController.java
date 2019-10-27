package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.QuestionDTO;
import com.mmt.ltxm.model.User;
import com.mmt.ltxm.service.Qusetionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private Qusetionservice qusetionservice;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action, Model model, HttpServletRequest httpServletRequest, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if(user==null){
            return "redirecti:/";
        }

        List<QuestionDTO> MyQuestion = qusetionservice.listById(user.getId(), model, start, size);
        model.addAttribute("MyQuestion", MyQuestion);
        return "profile";
    }
}
