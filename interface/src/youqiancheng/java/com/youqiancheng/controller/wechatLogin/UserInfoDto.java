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
package com.youqiancheng.controller.wechatLogin;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 功能：
 * 〈〉
 *
 * @author ytf
 * @create 2020/6/15
 * @since 1.0.0
 */
@Data
public class UserInfoDto {
    @ApiModelProperty(value = "用户信息",name ="nick")
    private Object entity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;
    @ApiModelProperty(value = "注册标记：1、未注册，2已注册",name ="flag")
    private int flag;

}
