package com.winter.mapper;

import com.winter.model.Project;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer projid);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer projid);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}