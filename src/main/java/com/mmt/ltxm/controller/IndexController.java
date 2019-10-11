package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.QuestionDTO;
import com.mmt.ltxm.mapper.Usermapper;
import com.mmt.ltxm.model.User;
import com.mmt.ltxm.service.Qusetionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private Usermapper usermapper;
    @Autowired
    private Qusetionservice qusetionservice;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest, Model model,@RequestParam(value = "start", defaultValue = "1") int start,@RequestParam(value = "size", defaultValue = "5") int size) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    User user = usermapper.findbytoken(cookie.getValue());
                    if (user != null) {
                        httpServletRequest.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionDTOS = qusetionservice.list(model,start,size);
//        PageInfo<QuestionDTO> page = new PageInfo<>(questionDTOS);
//        page.setPageNum(start);
//        System.out.println(page.toString());
        model.addAttribute("questionDTOS",questionDTOS);
        return "index";
    }
}
