/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: CustomerServiceService
 * Author:   ytf
 * Date:     2020/6/9 14:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.service.shop;

import com.youqiancheng.form.shop.CustomerServiceUpdateForm;
import com.youqiancheng.mybatis.model.D03CustomerServiceDO;
import com.youqiancheng.vo.result.ResultVo;

import java.util.List;
import java.util.Map;

/**
 * 功能：
 * 〈〉
 *
 * @author ytf
 * @create 2020/6/9
 * @since 1.0.0
 */
public interface CustomerServiceService {

    D03CustomerServiceDO get(Long id);

    List<D03CustomerServiceDO> list(Map<String, Object> map);
    List<D03CustomerServiceDO> listHDPage(Map<String, Object> map);
    List<D03CustomerServiceDO> listHDPageByManage(Map<String, Object> map);

    int count(Map<String, Object> map);



    int insertBatch(List<D03CustomerServiceDO> d03CustomerServices);



    int updateList(List<D03CustomerServiceDO> d03CustomerServices);

    int examineOrderPass(CustomerServiceUpdateForm form);
    ResultVo examineOrderPassApp(CustomerServiceUpdateForm form);

    int examineOrderRefuse(CustomerServiceUpdateForm form);
    int examineOrderRefuseApp(CustomerServiceUpdateForm form);
    ResultVo refundOrder(Long id);
}
