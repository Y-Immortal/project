package edu.wschina.a03.Bean;

public class Item {

    String name,juli,math;

    public Item(String name, String juli, String math) {
        this.name = name;
        this.juli = juli;
        this.math = math;
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

    public String getMath() {
        return math;
    }

    public void setMath(String math) {
        this.math = math;
    }
}
