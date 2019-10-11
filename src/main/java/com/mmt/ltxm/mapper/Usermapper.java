package com.mmt.ltxm.mapper;

import com.mmt.ltxm.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Usermapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from User where token = #{token}")
    User findbytoken(@Param("token") String token);
    @Select("select * from User where id = #{id}")
    User findById(@Param("id")Integer id);
}
