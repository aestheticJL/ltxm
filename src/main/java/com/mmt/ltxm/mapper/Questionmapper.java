package com.mmt.ltxm.mapper;

import com.mmt.ltxm.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Questionmapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
    @Select("select * from question")
    List<Question> list();
    @Select("select * from question where creator = #{userId}")
    List<Question> listById(@Param("userId") Integer userId);
    @Select("select * from question where id = #{id}")
    Question getById(@Param("id")Integer id);
    @Update("update question set description=#{description},title=#{title},tag=#{tag},gmt_modified=#{gmtModified} where id = #{id}")
    void update(Question question);
}
