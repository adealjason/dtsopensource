package org.github.dtsopensource.admin.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.github.dtsopensource.admin.dao.dataobject.ActivityActionRuleDO;
import org.github.dtsopensource.admin.dao.dataobject.ActivityActionRuleDOExample;

/**
 * @author ligaofeng 2016年12月23日 下午1:36:15
 */
public interface ActivityActionRuleDOMapper {
    /**
     * @param bizAction
     * @return
     */
    int deleteByPrimaryKey(String bizAction);

    /**
     * @param record
     * @return
     */
    int insert(ActivityActionRuleDO record);

    /**
     * @param record
     * @return
     */
    int insertSelective(ActivityActionRuleDO record);

    /**
     * @param example
     * @return
     */
    List<ActivityActionRuleDO> selectByExample(ActivityActionRuleDOExample example);

    /**
     * @param bizAction
     * @return
     */
    ActivityActionRuleDO selectByPrimaryKey(String bizAction);

    /**
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") ActivityActionRuleDO record,
                                 @Param("example") ActivityActionRuleDOExample example);

    /**
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") ActivityActionRuleDO record,
                        @Param("example") ActivityActionRuleDOExample example);

    /**
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ActivityActionRuleDO record);

    /**
     * @param record
     * @return
     */
    int updateByPrimaryKey(ActivityActionRuleDO record);
}
