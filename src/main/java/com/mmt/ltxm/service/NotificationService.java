package com.mmt.ltxm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmt.ltxm.dto.NotificationDTO;
import com.mmt.ltxm.enums.NotificationEnum;
import com.mmt.ltxm.enums.NotificationStatusEnum;
import com.mmt.ltxm.exception.CustomizeErrorCode;
import com.mmt.ltxm.exception.CustomizeException;
import com.mmt.ltxm.mapper.NotificationMapper;
import com.mmt.ltxm.mapper.UserMapper;
import com.mmt.ltxm.model.Notification;
import com.mmt.ltxm.model.NotificationExample;
import com.mmt.ltxm.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    public List<NotificationDTO> listById(Long userId, Model model, int start, int size) {
        PageHelper.startPage(start, size, "GMT_CREATE desc");
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
        PageInfo<Notification> page = new PageInfo<>(notifications);
        List pagelist = new ArrayList();
        for (int i = 1; i <= page.getPages(); i++) {
            pagelist.add(i);
        }
        model.addAttribute("pagelist", pagelist);
        model.addAttribute("page", page);
        List<NotificationDTO> notificationDTOS = new ArrayList();
        for (Notification notification : notifications) {
            User user = userMapper.selectByPrimaryKey(notification.getReceiver());
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setNotifier(user);
            notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        return notificationDTOS;
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
