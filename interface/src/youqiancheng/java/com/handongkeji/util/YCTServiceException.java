package com.handongkeji.util;

@SuppressWarnings("all")
public class YCTServiceException extends Exception {
	private static final long serialVersionUID = 23625480652400721L;
	private String errorMsg;
	private int errorKey = 0;

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorKey() {
		return this.errorKey;
	}

	public void setErrorKey(int errorKey) {
		this.errorKey = errorKey;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = 31 * result + ((this.errorMsg == null) ? 0 : this.errorMsg.hashCode());
		result = 31 * result + this.errorKey;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YCTServiceException other = (YCTServiceException) obj;
		if (this.errorMsg == null) {
			if (other.errorMsg == null)
				return (this.errorKey == other.errorKey);
			return false;
		}
		if (!(this.errorMsg.equals(other.errorMsg)))
			return false;
		return false;

	}

	public String toString() {
		return "AWServiceException [errorMsg=" + this.errorMsg + ", errorKey=" + this.errorKey + "]";
	}

	public YCTServiceException() {
	}

	public YCTServiceException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
		this.errorKey = 100;
	}

	public YCTServiceException(String errorMsg, int errorKey) {
		super(errorMsg);
		this.errorMsg = errorMsg;
		this.errorKey = errorKey;
	}

	public YCTServiceException(Exception exception) {
		this.errorMsg = exception.getLocalizedMessage();
		this.errorKey = 100;
	}

	public YCTServiceException(int errorKey, Throwable cause) {
		super(cause);
		this.errorMsg = cause.getLocalizedMessage();
		this.errorKey = errorKey;
	}
}