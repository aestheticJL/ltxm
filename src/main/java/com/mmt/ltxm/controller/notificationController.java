package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.NotificationDTO;
import com.mmt.ltxm.enums.NotificationEnum;
import com.mmt.ltxm.model.User;
import com.mmt.ltxm.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class notificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{outerId}")
    public String profile(@PathVariable("outerId") Long outerId, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(outerId, user);

        if (NotificationEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }

}
