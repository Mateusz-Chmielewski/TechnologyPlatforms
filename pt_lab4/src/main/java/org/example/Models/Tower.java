package org.example.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void setMages(List<Mage> mages) {
        this.mages = mages;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Mage> mages;

    @Override
    public String toString() {
        return "Tower{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", mages=" + mages.size() +
                '}';
    }
}

