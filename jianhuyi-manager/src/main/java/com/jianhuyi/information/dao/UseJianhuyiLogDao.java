package com.jianhuyi.information.dao;

import com.jianhuyi.information.domain.UploadRecordDO;
import com.jianhuyi.information.domain.UseJianhuyiLogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * 检测记录表
 *
 * @author wjl
 * @email bushuo@163.com
 * @date 2019-10-11 18:54:34
 */
@Mapper
public interface UseJianhuyiLogDao {

    UseJianhuyiLogDO get(Integer id);

    List<UseJianhuyiLogDO> list(Map<String, Object> map);

    List<UseJianhuyiLogDO> list1(Map<String, Object> map);

    List<UseJianhuyiLogDO> listDetail(Map<String, Object> map);

    int count(Map<String, Object> map);

    int countLog(Map<String, Object> map);

    int save(UseJianhuyiLogDO useJianhuyiLog);

    int update(UseJianhuyiLogDO useJianhuyiLog);

    int remove(Integer id);

    int batchRemove(Integer[] ids);

    int counts(Map<String, Object> map);

    List<UseJianhuyiLogDO> queryUserWeekRecord(@Param("saveTime") String saveTime, @Param("userId") Long userId);

    List<UseJianhuyiLogDO> queryUserWeekRecordBetween(@Param("start") Date startTime, @Param("end") Date endTime,
                                                      @Param("userId") Long userId);

    Collection<? extends UseJianhuyiLogDO> queryData(@Param("saveTime") String string, @Param("uploadId") Long uploadId, @Param("userId") Long userId);

    List<Map<String, Object>> exeList(@Param("saveTime") String string, @Param("uploadId") Long uploadId, @Param("userId") Long userId);


    LinkedList<UseJianhuyiLogDO> selectPersonAndDate(Map<String, Object> map);

    LinkedList<UseJianhuyiLogDO> selectAllData(Map<String, Object> map);

    int uploadRecordCount(Map<String, Object> params);

    List<UploadRecordDO> uploadRecordList(Map<String, Object> params);
}
