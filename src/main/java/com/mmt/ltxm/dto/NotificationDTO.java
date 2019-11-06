package com.mmt.ltxm.dto;

import com.mmt.ltxm.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer type;
    private Long outerid;
}
