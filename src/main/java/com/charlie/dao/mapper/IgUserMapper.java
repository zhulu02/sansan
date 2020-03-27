package com.charlie.dao.mapper;

import com.charlie.entity.IgUser;
import com.charlie.entity.IgUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IgUserMapper {
    int countByExample(IgUserExample example);

    int deleteByExample(IgUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(IgUser record);

    int insertSelective(IgUser record);

    List<IgUser> selectByExample(IgUserExample example);

    IgUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") IgUser record, @Param("example") IgUserExample example);

    int updateByExample(@Param("record") IgUser record, @Param("example") IgUserExample example);

    int updateByPrimaryKeySelective(IgUser record);

    int updateByPrimaryKey(IgUser record);
}