package com.youqiancheng.util.email;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendEmailDO implements Serializable {

	private String code;

	private Long sendTime;

}
