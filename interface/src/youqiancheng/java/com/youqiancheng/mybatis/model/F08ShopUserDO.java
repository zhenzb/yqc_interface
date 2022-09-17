package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.handongkeji.dto.BaseEntity;
import com.handongkeji.dto.RouterDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Author tyf
 * Date  2020-04-07
 */
@Data
@ApiModel(value = "商家用户实体")
@TableName("f08_shop_user")
public class F08ShopUserDO extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户Id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "账号：手机号",name ="userName")
    private String userName;
    @ApiModelProperty(value = "密码",name ="pwd")
    private String pwd;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "商家名称",name ="shopName")
    private String shopName;
    @ApiModelProperty(value = "商家账号冻结",name ="frozenAccount    状态:1:未冻结,2:已冻结")
    private int frozenAccount;
    @TableField(value = "status",fill = FieldFill.INSERT)
    @ApiModelProperty("状态 启用：1  停用：2  默认是1")
    private Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "商家类型",name="shopType")
    private int shopType;

    @TableField(exist = false)
    List<RouterDTO> routerDTOList;

    //token
    @TableField(exist = false)
    @ApiModelProperty("返回的token")
    private String token;

    @TableField(exist = false)
    @ApiModelProperty("角色idList")
    private List<Long> roleList;

}
