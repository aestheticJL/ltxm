package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.AccessTokoenDTO;
import com.mmt.ltxm.dto.GithubUser;
import com.mmt.ltxm.provider.Githubprovider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthorizeController {
    @Value("${github.client_id}") private String client_id;
    @Value("${github.client_secret}")private String client_secret;
    @Value("${github.redirect_uri}")private String redirect_uri;



    @Autowired
    private Githubprovider githubprovider;
    @GetMapping("/callback")
    public String comeback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokoenDTO accessTokoenDTO = new AccessTokoenDTO();
        accessTokoenDTO.setRedirect_uri(redirect_uri);
        accessTokoenDTO.setClient_id(client_id);
        accessTokoenDTO.setClient_secret(client_secret);
        accessTokoenDTO.setCode(code);
        accessTokoenDTO.setState(state);
        String accessToken = githubprovider.getAccessToken(accessTokoenDTO);
        GithubUser user = githubprovider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
