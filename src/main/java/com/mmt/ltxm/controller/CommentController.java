package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.CommentCreateDTO;
import com.mmt.ltxm.dto.CommentDTO;
import com.mmt.ltxm.dto.ResultDTO;
import com.mmt.ltxm.enums.CommentTypeEnum;
import com.mmt.ltxm.exception.CustomizeErrorCode;
import com.mmt.ltxm.model.Comment;
import com.mmt.ltxm.model.User;
import com.mmt.ltxm.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
//            throw new CustomizeException(CustomizeErrorCode.NOT_LOGIN);
        }

        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());
        comment.setCommentCount(0);
        commentService.insert(comment, user);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> get(@PathVariable("id") Long id, Model model) {
        List<CommentDTO> comment = commentService.listByQuestionId(id,CommentTypeEnum.COMMENT);
        model.addAttribute("comment", comment);
        return ResultDTO.okOf(comment);
    }
}
