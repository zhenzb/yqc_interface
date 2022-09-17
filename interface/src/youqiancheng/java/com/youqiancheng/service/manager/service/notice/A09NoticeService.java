package com.youqiancheng.service.manager.service.notice;


import com.youqiancheng.form.manager.notice.A09NoticeDeleteForm;
import com.youqiancheng.mybatis.model.A09NoticeDO;

import java.util.List;
import java.util.Map;

public interface A09NoticeService {
    List<A09NoticeDO> listNoticePage(Map<String, Object> map);
    A09NoticeDO getNoticeById(long id);

    boolean deleteNotice(A09NoticeDeleteForm a09NoticeDeleteForm);

    int deleteBatch(List<Long> ids);

    int save(A09NoticeDO a09NoticeDO);
    int update(A09NoticeDO a09NoticeDO);

    int release(List<Long> ids);
}
