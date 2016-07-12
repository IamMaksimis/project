package com.iammaksimus.recipes;

/**
 * Created by 111 on 01.07.2016.
 */
public class Calories {
    float
            b,
            g,
            u,
            k;
    Calories(){
        this.b = 0;
        this.g = 0;
        this.u = 0;
        this.k = 0;
    }

    public void add(String b, String g, String u, String k){
        this.b += Float.parseFloat(b.replace(",", ".").replace("-", "0"));
        this.g += Float.parseFloat(g.replace(",", ".").replace("-", "0"));
        this.u += Float.parseFloat(u.replace(",", ".").replace("-", "0"));
        this.k += Float.parseFloat(k.replace(",", ".").replace("-", "0"));
    }

    public float getB() {
        return b;
    }

    public float getG() {
        return g;
    }

    public float getK() {
        return k;
    }

    public float getU() {
        return u;
    }
}
