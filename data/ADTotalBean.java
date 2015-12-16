package com.mtime.beans;

import java.util.List;

public class ADTotalBean {
	private boolean success;
	private String error;
	private int count;
	private List<ADDetailBean> advList;

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError() {
              if (error==null) {
                  return "";
              }
              else{
                  return error;
              }
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<ADDetailBean> getAdvList() {
		return advList;
	}

	public void setAdvList(List<ADDetailBean> advList) {
		this.advList = advList;
	}
}
