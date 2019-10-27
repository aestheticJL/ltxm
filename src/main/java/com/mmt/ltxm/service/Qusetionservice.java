package com.mmt.ltxm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmt.ltxm.dto.QuestionDTO;
import com.mmt.ltxm.exception.CustomizeErrorCode;
import com.mmt.ltxm.exception.CustomizeException;
import com.mmt.ltxm.mapper.QuestionExtMapper;
import com.mmt.ltxm.mapper.QuestionMapper;
import com.mmt.ltxm.mapper.UserMapper;
import com.mmt.ltxm.model.Question;
import com.mmt.ltxm.model.QuestionExample;
import com.mmt.ltxm.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Qusetionservice {
    @Autowired
    private UserMapper usermapper;
    @Autowired
    private QuestionMapper questionmapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;


    public List<QuestionDTO> listById(Model model, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        PageHelper.startPage(start, size, "gmt_modified desc");
        List<Question> questions = questionmapper.selectByExample(new QuestionExample());
        PageInfo<Question> page = new PageInfo<>(questions);
        List pagelist = new ArrayList();
        for (int i = 1; i <= page.getPages(); i++) {
            pagelist.add(i);
        }
        model.addAttribute("pagelist", pagelist);
        model.addAttribute("page", page);
        List<QuestionDTO> questionDTOlist = new ArrayList();
        for (Question question : questions) {
            User user = usermapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOlist.add(questionDTO);
        }
        return questionDTOlist;
    }

    public List<QuestionDTO> listById(Long userId, Model model, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        PageHelper.startPage(start, size, "gmt_modified desc");
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionmapper.selectByExample(questionExample);
        PageInfo<Question> page = new PageInfo<>(questions);
        List pagelist = new ArrayList();
        for (int i = 1; i <= page.getPages(); i++) {
            pagelist.add(i);
        }
        System.out.println(page);
        model.addAttribute("pagelist", pagelist);
        model.addAttribute("page", page);
        List<QuestionDTO> questionDTOlist = new ArrayList();
        for (Question question : questions) {
            User user = usermapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOlist.add(questionDTO);
        }
        return questionDTOlist;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionmapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = usermapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question, User user) {
        if (question.getId() == null) {
            question.setCreator(user.getId());
            question.setGmtCreate(user.getGmtCreate());
            question.setGmtModified(System.currentTimeMillis());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionmapper.insert(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updat = questionmapper.updateByExampleSelective(question, questionExample);
            if (updat != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
//        Question question = questionmapper.selectByPrimaryKey(id);
//        question.setViewCount(question.getViewCount()+1);
//        questionmapper.updateByPrimaryKey(question);


//        Question updateQuestion = new Question();
//        Question question = questionmapper.selectByPrimaryKey(id);
//        updateQuestion.setViewCount(question.getViewCount() + 1);
//        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria().andIdEqualTo(id);
//        questionmapper.updateByExampleSelective(updateQuestion, questionExample);

        questionmapper.updateViewCount(id, 1);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] split = StringUtils.split(questionDTO.getTag(), ",");
        String collect = Arrays.stream(split).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(collect);

        List<Question> questions = questionmapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());

        return questionDTOS;
    }
}
