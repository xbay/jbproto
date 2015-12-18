package com.mtime.beans;

public class ActorFestivals {
    
    private int festivalId;
    private String festivalImg;
    private String nameCn;
    private String shortName;
    private String nameEn;
    private User user;
    
    public int getFestivalId() {
        return festivalId;
    }
    public void setFestivalId(int festivalId) {
        this.festivalId = festivalId;
    }
    public String getFestivalImg() {
        return festivalImg;
    }
    public void setFestivalImg(String festivalImg) {
        this.festivalImg = festivalImg;
    }
    public String getNameCn() {
        return nameCn;
    }
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }
    public String getShortName() {
        return shortName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public class User{
        private String shortName;
        private String nameEn;
    }
    
}
