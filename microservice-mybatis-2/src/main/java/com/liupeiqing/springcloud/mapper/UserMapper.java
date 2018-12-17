package com.liupeiqing.springcloud.mapper;

import com.liupeiqing.springcloud.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liupeqing
 * @date 2018/12/17 17:13
 */
@Repository
@Mapper
public interface  UserMapper {

    @Select("SELECT * FROM T_USER WHERE PHONE = #{phone}")
    User findUserByPhone(@Param("phone") String phone);

    @Insert("INSERT INTO T_USER(USER_NAME, PASSWORD, PHONE) VALUES(#{user_name}, #{password}, #{phone})")
    int insert(@Param("name") String name, @Param("password") String password, @Param("phone") String phone);

    @Insert("INSERT INTO T_USER(USER_NAME, PASSWORD, PHONE) VALUES(" +
            "#{user_name, jdbcType=VARCHAR}, #{password, jdbcType=VARCHAR}, #{phone, jdbcType=VARCHAR})")
    int insertByMap(Map<String, Object> map);

    @Insert("INSERT INTO T_USER(USER_NAME, PASSWORD, PHONE) VALUES(#{user_name}, #{password}, #{phone})")
    int insertByUser(User user);

    @Update("UPDATE T_USER SET USER_NAME = #{user_name}, PASSWORD = #{password} WHERE PHONE = #{phone}")
    void update(User user);

    @Delete("DELETE FROM T_USER WHERE USER_ID = #{userId}")
    void delete(Integer id);

    @Results({
            @Result(property = "userName", column = "USER_NAME"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "phone", column = "PHONE")
    })
    @Select("SELECT USER_NAME, PASSWORD, PHONE FROM T_USER")
    List<User> findAll();
}
