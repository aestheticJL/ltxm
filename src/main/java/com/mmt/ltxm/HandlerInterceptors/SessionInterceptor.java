package com.mmt.ltxm.HandlerInterceptors;

import com.mmt.ltxm.enums.NotificationStatusEnum;
import com.mmt.ltxm.mapper.NotificationMapper;
import com.mmt.ltxm.mapper.UserMapper;
import com.mmt.ltxm.model.NotificationExample;
import com.mmt.ltxm.model.User;
import com.mmt.ltxm.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.management.Notification;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = usermapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                        NotificationExample notificationExample = new NotificationExample();
                        notificationExample.createCriteria().andNotifierEqualTo(users.get(0).getId()).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
                        long unreadCount = notificationMapper.countByExample(notificationExample);
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
