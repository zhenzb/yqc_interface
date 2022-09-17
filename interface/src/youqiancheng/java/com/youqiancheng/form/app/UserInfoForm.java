/**
 * Copyright (C), 2015-2020, 撼动科技有限公司
 * FileName: UserInfoDto
 * Author:   ytf
 * Date:     2020/6/15 10:55
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.youqiancheng.form.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能：
 * 〈〉
 *
 * @author ytf
 * @create 2020/6/15
 * @since 1.0.0
 */
@Data
public class UserInfoForm {
    @ApiModelProperty(value = "用户信息",name ="nick")
    private Object entity;
    @ApiModelProperty(value = "注册标记：1、未注册，2已注册",name ="flag")
    private int flag;
    @ApiModelProperty(value = "联系客服签名",name ="userSig")
    private String userSig;
    @ApiModelProperty(value = "",name ="tengXunImId")
    private String tengXunImId;

}
