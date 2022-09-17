package com.youqiancheng.vo.client;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class D03CustomerVo {

    private long userId;

    private LocalDateTime createTime;

    private Integer number;
}
