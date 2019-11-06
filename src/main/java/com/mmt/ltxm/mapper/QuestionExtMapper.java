package com.mmt.ltxm.mapper;

import com.mmt.ltxm.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    List<Question> selectRelated(Question question);

    List<Question> countBySearch(String search);

}
