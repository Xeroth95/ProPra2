package de.hhu.propra14.team132.gameObjects;

import com.google.gson.annotations.Expose;

/**
 * Created by isabel on 16.05.14.
 */
public class Test {
    transient int a;
    int b;
    @Expose
    int c;
    public Test (int a, int b, int c) {
        this.a=a;
        this.c=c;
        this.b=b;


    }
}
