package com.handongkeji.util;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class JsonBean
  implements Serializable
{
  public static final long serialVersionUID = 46601L;
  private Integer Status;
  private String Message;
  private Object Data;
  private Object content;
  private String type;

  public JsonBean()
  {
  }

  public JsonBean(String message, Object data, Object content, String type)
  {
    this.Message = message;
    this.Data = data;
    this.content = content;
    this.type = type;
  }

  public JsonBean(String message, Object data, Object content, String type, Object token)
  {
    this.Message = message;
    this.Data = data;
    this.content = content;
    this.type = type;
  }

  public JsonBean(String message, Object data, Object content)
  {
    this.Message = message;
    this.Data = data;
    this.content = content;
  }

  public JsonBean(String message, Object data)
  {
    this.Message = message;
    this.Data = data;
  }

  public Integer getStatus()
  {
    return this.Status;
  }

  public void setStatus(Integer status)
  {
    this.Status = status;
  }

  public String getMessage()
  {
    return this.Message;
  }

  public void setMessage(String message)
  {
    this.Message = message;
  }

  public Object getData()
  {
    return this.Data;
  }

  public void setData(Object data)
  {
    this.Data = data;
  }

  public Object getContent()
  {
    return this.content;
  }

  public void setContent(Object content)
  {
    this.content = content;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }
}