package com.mmt.ltxm.controller;

import com.mmt.ltxm.mapper.Usermapper;
import com.mmt.ltxm.model.User;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    Usermapper usermapper;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                User user = usermapper.findbytoken(cookie.getValue());
                if (user!=null){
                    httpServletRequest.getSession().setAttribute("user",user );
                }
                break;
            }
        }
        return "index";
    }
}
