package com.handongkeji.util;

import java.io.Serializable;

public class ReturnData<E>
  implements Serializable
{
  private static final long serialVersionUID = 6374477350236329480L;
  protected int code = 1;
  protected String msg;
  protected String token;
  protected E data;

  public int getCode()
  {
    return this.code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public E getData() {
    return this.data;
  }

  public void setData(E data) {
    this.data = data;
  }

  public String getToken()
  {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}