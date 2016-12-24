package org.github.dtsopensource.admin.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.github.dtsopensource.admin.dao.dataobject.ActivityRuleDO;
import org.github.dtsopensource.admin.dao.dataobject.ActivityRuleDOExample;

/**
 * @author ligaofeng 2016年12月23日 下午1:37:08
 */
public interface ActivityRuleDOMapper {
    /**
     * @param bizType
     * @return
     */
    int deleteByPrimaryKey(String bizType);

    /**
     * @param record
     * @return
     */
    int insert(ActivityRuleDO record);

    /**
     * @param record
     * @return
     */
    int insertSelective(ActivityRuleDO record);

    /**
     * @param example
     * @return
     */
    List<ActivityRuleDO> selectByExample(ActivityRuleDOExample example);

    /**
     * @param bizType
     * @return
     */
    ActivityRuleDO selectByPrimaryKey(String bizType);

    /**
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(@Param("record") ActivityRuleDO record,
                                 @Param("example") ActivityRuleDOExample example);

    /**
     * @param record
     * @param example
     * @return
     */
    int updateByExample(@Param("record") ActivityRuleDO record, @Param("example") ActivityRuleDOExample example);

    /**
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(ActivityRuleDO record);

    /**
     * @param record
     * @return
     */
    int updateByPrimaryKey(ActivityRuleDO record);
}
