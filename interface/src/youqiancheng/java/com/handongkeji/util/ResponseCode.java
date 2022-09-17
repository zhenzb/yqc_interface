package com.handongkeji.util;

import java.io.Serializable;

public class ResponseCode
  implements Serializable
{
  private static final long serialVersionUID = 5877785743492180268L;
  public static final int FAIL = 0;
  public static final int OK = 1;
  public static final int SERVICE = 100;
  public static final int TOKEN_ERROR = 200;
  public static final int TOKEN_TIMEOUT = 300;
}