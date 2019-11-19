package com.mmt.ltxm.mapper;

import com.mmt.ltxm.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionExtMapper {
    List<Question> selectRelated(Question question);

    List<Question> selectBySearch(@Param("search") String search, @Param("tag")String tag);

}
