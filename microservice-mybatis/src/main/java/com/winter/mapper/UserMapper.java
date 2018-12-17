package com.winter.mapper;

import com.winter.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liupeqing
 * @date 2018/12/17 15:43
 */
@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAllUser();
}
