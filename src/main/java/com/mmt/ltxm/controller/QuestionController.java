package com.mmt.ltxm.controller;

import com.mmt.ltxm.dto.CommentDTO;
import com.mmt.ltxm.dto.QuestionDTO;
import com.mmt.ltxm.enums.CommentTypeEnum;
import com.mmt.ltxm.service.CommentService;
import com.mmt.ltxm.service.Qusetionservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private Qusetionservice qusetionservice;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String Question(@PathVariable("id") Long id, Model model){
        QuestionDTO questionDTO = qusetionservice.getById(id);
        List<QuestionDTO> relatedQuestions = qusetionservice.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByQuestionId(id, CommentTypeEnum.QUESTION);
        qusetionservice.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }


}
