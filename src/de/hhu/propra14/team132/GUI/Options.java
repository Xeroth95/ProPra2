package de.hhu.propra14.team132.GUI;

import java.io.Serializable;

/**
 * Created by fabian on 15.05.14.
 */
public class Options implements Serializable{
    public static int ARROWS=1;
    public static int WASD=2;

    public int bgVolume;
    public int fxVolume;
    public int controls;
    public int roundLength;
    public int wormsNumber;

    public void setStandard() {
        this.bgVolume=100;
        this.fxVolume=100;
        this.controls=ARROWS;
        this.roundLength=120;
        this.wormsNumber=4;
    }

    public void save() {

    }

    public void setBgVolume(int bgVolume) {
        this.bgVolume=bgVolume;
    }

    public int getBgVolume() {
        return bgVolume;
    }

    public void setFxVolume(int fxVolume) {
        this.fxVolume=fxVolume;
    }

    public int getFxVolume() {
        return fxVolume;
    }

    public void setControls(int controls) {
        this.controls=controls;
    }

    public int getControls() {
        return controls;
    }

    public void setRoundLength(int roundLength) {
        this.roundLength=roundLength;
    }

    public int getRoundLength() {
        return roundLength;
    }

    public void setWormsNumber(int wormsNumber) {
        this.wormsNumber=wormsNumber;
    }

    public int getWormsNumber() {
        return wormsNumber;
    }
}
