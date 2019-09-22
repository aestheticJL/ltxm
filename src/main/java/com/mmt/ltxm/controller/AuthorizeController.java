package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.AccessTokoenDTO;
import com.mmt.ltxm.provider.Githubprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private Githubprovider githubprovider;
    public String comeback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokoenDTO accessTokoenDTO = new AccessTokoenDTO();
        accessTokoenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokoenDTO.setClient_id("8f8f94f282f9703b6d8c");
        accessTokoenDTO.setClient_secret("cc8a035bef43562c930425d9eeeef2c7ab1785b4");
        accessTokoenDTO.setCode(code);
        accessTokoenDTO.setState(state);
        githubprovider.getAccessToken(accessTokoenDTO);
        return "index";
    }
}
