package edu.wschina.a66.Bean;

public class Image {

    String name,juli,jiage,reviews;

    int img;

    public Image(String name, String juli, String jiage, String reviews, int img) {
        this.name = name;
        this.juli = juli;
        this.jiage = jiage;
        this.reviews = reviews;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public String getJiage() {
        return jiage;
    }

    public void setJiage(String jiage) {
        this.jiage = jiage;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
