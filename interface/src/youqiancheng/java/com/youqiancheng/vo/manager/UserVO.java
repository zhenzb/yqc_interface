package com.youqiancheng.vo.manager;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {
    @ApiModelProperty("今日新增用户数量")
    private Long userForDay;
    @ApiModelProperty("本月新增用户数量")
    private Long userForMonth;
    @ApiModelProperty("用户总数量")
    private Long userForAll;
}
