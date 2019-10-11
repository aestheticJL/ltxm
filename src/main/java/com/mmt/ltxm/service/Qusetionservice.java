package com.mmt.ltxm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmt.ltxm.dto.QuestionDTO;
import com.mmt.ltxm.mapper.Questionmapper;
import com.mmt.ltxm.mapper.Usermapper;
import com.mmt.ltxm.model.Question;
import com.mmt.ltxm.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class Qusetionservice {
    @Autowired
    private Usermapper usermapper;
    @Autowired
    private Questionmapper questionmapper;


    public List<QuestionDTO> list(Model model,@RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        PageHelper.startPage(start,size,"id asc");
        List<Question> questions = questionmapper.list();
        PageInfo<Question> page = new PageInfo<>(questions);
        List pagelist = new ArrayList();
        for (int i = 1;i <= page.getPages();i++){
            pagelist.add(i);
        }
        System.out.println(page);
        model.addAttribute("pagelist",pagelist);
        model.addAttribute("page",page);
        List<QuestionDTO> questionDTOlist = new ArrayList();
        for (Question question : questions) {
            User user = usermapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOlist.add(questionDTO);
        }
        return questionDTOlist;
    }
}
