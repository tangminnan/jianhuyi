package com.jianhuyi.information.service;


import com.jianhuyi.information.domain.UseRemindsDO;

import java.util.List;
import java.util.Map;

/**
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-07-22 09:43:53
 */
public interface UseRemindsService {

    UseRemindsDO get(Long id);

    List<UseRemindsDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(UseRemindsDO useReminds);

    int update(UseRemindsDO useReminds);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
