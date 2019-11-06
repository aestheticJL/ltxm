package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.AccessTokoenDTO;
import com.mmt.ltxm.dto.GithubUser;
import com.mmt.ltxm.model.User;
import com.mmt.ltxm.provider.Githubprovider;
import com.mmt.ltxm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Value("${github.client_id}")
    private String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_uri}")
    private String redirect_uri;

    @Autowired
    private UserService userService;

    @Autowired
    private Githubprovider githubprovider;

    @GetMapping("/callback")
    public String comeback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse httpServletResponse) {
        AccessTokoenDTO accessTokoenDTO = new AccessTokoenDTO();
        accessTokoenDTO.setRedirect_uri(redirect_uri);
        accessTokoenDTO.setClient_id(client_id);
        accessTokoenDTO.setClient_secret(client_secret);
        accessTokoenDTO.setCode(code);
        accessTokoenDTO.setState(state);
        String accessToken = githubprovider.getAccessToken(accessTokoenDTO);
        GithubUser githubUser = githubprovider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            httpServletResponse.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        return "redirect:/";
    }
}
