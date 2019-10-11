package com.mmt.ltxm.dto;

import com.mmt.ltxm.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
