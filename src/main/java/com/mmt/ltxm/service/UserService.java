package com.mmt.ltxm.service;

import com.mmt.ltxm.mapper.Usermapper;
import com.mmt.ltxm.model.User;
import com.mmt.ltxm.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private Usermapper usermapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = usermapper.selectByExample(userExample);
        if(users.size() == 0){
            user.setGmtModified(System.currentTimeMillis());
            user.setGmtCreate(user.getGmtCreate());
            usermapper.insert(user);
        }else {
            User dbUser = users.get(0);
            User UpdateUser = new User();
            UpdateUser.setGmtModified(System.currentTimeMillis());
            UpdateUser.setName(user.getName());
            UpdateUser.setToken(user.getToken());
            UpdateUser.setAvatarUrl(user.getAvatarUrl());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            usermapper.updateByExampleSelective(UpdateUser,example);
        }
    }
}
