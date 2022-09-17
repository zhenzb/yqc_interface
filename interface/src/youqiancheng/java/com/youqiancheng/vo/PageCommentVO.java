package com.youqiancheng.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author Gavin
 * 微信公众号分页返回对象
 */
@Data
public class PageCommentVO<T> {
    // 总页数
    private Integer totalPage;
    // 列表
    private List<T> list;
    // 其他数据
    private Map<String,Object> map;

    public PageCommentVO() {
    }

    public PageCommentVO(Integer totalPage, List<T> list) {
        this.totalPage = totalPage;
        this.list = list;
    }
    public PageCommentVO(Integer totalPage, Map<String, Object> map) {
        this.totalPage = totalPage;
        this.map = map;
    }
    public PageCommentVO(Integer totalPage, List<T> list, Map<String, Object> map) {
        this.totalPage = totalPage;
        this.list = list;
        this.map = map;
    }
}
