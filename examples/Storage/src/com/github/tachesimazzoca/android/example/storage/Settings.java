package com.github.tachesimazzoca.android.example.storage;

import java.io.Serializable;
import java.util.EnumSet;

public class Settings implements Serializable {
    private static final long serialVersionUID = -3161994647227901691L;

    public enum Colors {
        RED, GREEN, BLUE
    }

    public enum YesNo {
        NO, YES, MAYBE
    }

    public final EnumSet<Colors> colors;
    public final YesNo yesNo;

    public Settings() {
        this.colors = EnumSet.noneOf(Colors.class);
        this.yesNo = YesNo.YES;
    }

    public Settings(EnumSet<Colors> colors, YesNo yesNo) {
        this.colors = colors;
        this.yesNo = yesNo;
    }

    public Settings updateYesNo(YesNo theYesNo) {
        return new Settings(this.colors, theYesNo);
    }

    public Settings updateColor(Colors theColor, boolean checked) {
        EnumSet<Colors> newColors = this.colors.clone();
        if (checked) {
            newColors.add(theColor);
        } else {
            newColors.remove(theColor);
        }
        return new Settings(newColors, this.yesNo);
    }

    public Settings addColor(Colors theColor) {
        return updateColor(theColor, true);
    }

    public Settings removeColor(Colors theColor) {
        return updateColor(theColor, false);
    }
}