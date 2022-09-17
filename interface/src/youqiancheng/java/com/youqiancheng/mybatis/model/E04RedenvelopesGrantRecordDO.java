package com.youqiancheng.mybatis.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author tyf
 * Date  2020-04-01
 */
@Data
@ApiModel(value = "红包发放记录实体")
@TableName("e04_redenvelopes_grant_record")
public class E04RedenvelopesGrantRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id(数据库自增)",name ="id",example = "1",notes = "自增主键在新增时无需传入")
    private long id;
    @ApiModelProperty(value = "红包街ID",name ="streetId")
    private long streetId;
    @ApiModelProperty(value = "商家ID",name ="shopId")
    private long shopId;
    @ApiModelProperty(value = "创建人",name ="createPerson")
    private String createPerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间",name ="createTime")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "修改人",name ="updatePerson")
    private String updatePerson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间",name ="updateTime")
    private LocalDateTime updateTime;
    @ApiModelProperty(value = "删除标识",name ="deleteFlag")
    private int deleteFlag;
    @ApiModelProperty(value = "结束标识",name ="endFlag")
    private int endFlag;
    @ApiModelProperty(value = "支付类型",name ="payType")
    private int payType;
    @ApiModelProperty(value = "支付流水",name ="payNo")
    private String payNo;
    @ApiModelProperty(value = "发放红包金额",name ="money")
    private BigDecimal money;

    @TableField(exist = false)
    @ApiModelProperty(value = "商家名称",name="shopName")
    private String shopName;

    @TableField(exist = false)
    @ApiModelProperty(value = "街道名称",name="streetName")
    private String streetName;

}
