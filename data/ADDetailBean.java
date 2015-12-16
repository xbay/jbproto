package com.mtime.beans;


public class ADDetailBean implements BeanTypeInterface{
	private String type;
	private String typeName;
	private boolean isHorizontalScreen;
	private int startDate;
	private int endDate;
	private String url;
	private String tag;
	private boolean isOpenH5;

	public String getType() {
	    return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName() {
	    return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public boolean getIsHorizontalScreen() {
		return isHorizontalScreen;
	}

	public void setIsHorizontalScreen(boolean isHorizontalScreen) {
		this.isHorizontalScreen = isHorizontalScreen;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public String getUrl() {
	    return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTag() {
	    return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean getIsOpenH5() {
		return isOpenH5;
	}

	public void setIsOpenH5(boolean isOpenH5) {
		this.isOpenH5 = isOpenH5;
	}

    @Override
    public int getBeanType() {
        return BeanTypeInterface.TYPE_ADV_RECOMMEND;
    }
}
