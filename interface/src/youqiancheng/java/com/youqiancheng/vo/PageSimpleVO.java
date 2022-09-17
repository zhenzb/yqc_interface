package com.youqiancheng.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author Gavin
 * 分页返回对象
 */
@Data
public class PageSimpleVO<T> {

    //当前页
    private Integer currentPage;
    // 总数
    private Integer totalNumber;
    // 列表
    private List<T> list;
    // 其他数据
    private Map<String,Object> map;
    public PageSimpleVO() {
    }

    public PageSimpleVO(Integer totalNumber, List<T> list) {
        this.totalNumber = totalNumber;
        this.list = list;
    }

    public PageSimpleVO(Integer totalNumber, List<T> list, Map<String, Object> map) {
        this.totalNumber = totalNumber;
        this.list = list;
        this.map = map;
    }
}
