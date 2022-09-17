package com.youqiancheng.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 通用查询数据传输对象，包括查询字段、排序字段、前端筛选列表
 * */
@ApiModel(value="",description="")
@Data
public class CommonSearchInputDto  {
    /**
     * 排序字段
     */
    @Size(max = 30)
    @ApiModelProperty(value = "排序字段")
    private String sort ;
    /**
     * 排序模式
     */
    @Size(max = 30)
    @ApiModelProperty(value = "排序模式")
    private String order ;


}
