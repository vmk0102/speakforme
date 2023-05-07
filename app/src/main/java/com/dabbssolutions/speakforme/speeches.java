package com.dabbssolutions.speakforme;

public class speeches {
    private String ImageName;
    private boolean isSentence;
    public speeches(String ImageName,boolean isSentence){
        this.ImageName=ImageName;
        this.isSentence=isSentence;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public boolean isSentence() {
        return isSentence;
    }

    public void setSentence(boolean sentence) {
        isSentence = sentence;
    }

}
