package com.github.tachesimazzoca.android.example.storage;

import java.io.Serializable;
import java.util.EnumSet;

public final class Settings implements Serializable {
    private static final long serialVersionUID = 2L;

    public enum Colors {
        RED(1, "Red"), GREEN(2, "Green"), BLUE(3, "Blue");

        public final int id;
        public final String label;

        private Colors(int id, String label) {
            this.id = id;
            this.label = label;
        }
    }

    public enum YesNo {
        NO(0, "No"), YES(1, "Yes"), MAYBE(2, "Maybe");

        public final int id;
        public final String label;

        private YesNo(int id, String label) {
            this.id = id;
            this.label = label;
        }
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
