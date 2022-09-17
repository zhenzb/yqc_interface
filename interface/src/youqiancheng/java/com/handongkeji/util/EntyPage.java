package com.handongkeji.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页对应的实体类
 */
@Data
public class EntyPage implements java.io.Serializable {

    public EntyPage() {
    }

    public EntyPage(@NotNull(message = "请选择第几页") @Min(message = "分页参数错误", value = 1) int currentPage, @NotNull(message = "请填写每页查询数量") @Min(value = 1, message = "分页参数不能小于1") @Max(value = 50, message = "分页参数不能大于50") int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1173808L;
    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数",hidden = true)
    private int totalNumber;
    /**
     * 当前第几页
     */
    @NotNull(message = "请选择第几页")
    @Min(message = "分页参数错误", value = 1)
    @ApiModelProperty(value = "当前第几页")
    private int currentPage=1;
    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数",hidden = true)
    private int totalPage;
    /**
     * 每页显示条数
     */
    @NotNull(message = "请填写每页查询数量")
    @Min(value = 1, message = "分页参数不能小于1")
    @Max(value = 100, message = "分页参数不能大于100")
    @ApiModelProperty("每页几条")
    private int pageSize = 10;

    @ApiModelProperty(value = "数据库中limit的参数，从第几条开始取",hidden = true)
    private int dbOffset;

    @ApiModelProperty(value = "数据库中limit的参数，一共取多少条",hidden = true)
    private int dbSize;

    @ApiModelProperty(value = "有下一页",hidden = true)
    private Boolean hasNextPage;

    @ApiModelProperty(value = "有上一页",hidden = true)
    private Boolean hasLastPage;

    @ApiModelProperty(value = "下一页",hidden = true)
    private Integer nextPage;

    @ApiModelProperty(value = "上一页",hidden = true)
    private Integer lastPage;

    // 该页是否有下一页.
    @ApiModelProperty(hidden = true)
    public Boolean getHasNextPage() {
        hasNextPage = currentPage < totalPage;
        return hasNextPage;
    }

    // 该页是否有上一页.
    @ApiModelProperty(hidden = true)
    public Boolean getHasLastPage() {
        hasLastPage = currentPage > 1;
        return hasLastPage;
    }

    // 获取上一页页数
    @ApiModelProperty(hidden = true)
    public int getLastPage() {
        if (getHasLastPage()) {
            lastPage = currentPage - 1;
        } else {
            lastPage = currentPage;
        }
        return lastPage;
    }
    @ApiModelProperty(hidden = true)
    public int getNextPage() {
        if (getHasNextPage()) {
            nextPage = currentPage + 1;
        } else {
            nextPage = currentPage;
        }
        return nextPage;
    }

    /**
     * 根据当前对象中属性值计算并设置相关属性值
     */
    @ApiModelProperty(hidden = true)
    public void count() {
        // 计算总页数
        int totalPageTemp = this.totalNumber / this.pageSize;
        int plus = (this.totalNumber % this.pageSize) == 0 ? 0 : 1;
        totalPageTemp = totalPageTemp + plus;
        if (totalPageTemp <= 0) {
            totalPageTemp = 1;
        }
        this.totalPage = totalPageTemp;

        // 当前页数小于1设置为1
        if (this.currentPage < 1) {
            this.currentPage = 1;
        }

        // 设置limit的参数
        this.dbOffset = (this.currentPage - 1) * this.pageSize;
        this.dbSize = this.pageSize;
    }
    @ApiModelProperty(hidden = true)
    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
        this.count();
    }

}
