package me.dreamdevs.github.randomlootchest.api.menu;

import lombok.Getter;

@Getter
public enum MenuSize {

    ONE_ROW(9), TWO_ROWS(18), THREE_ROWS(27), FOUR_ROWS(36), FIVE_ROWS(45), SIX_ROWS(54);

    private final int size;

    MenuSize(int size) {
        this.size = size;
    }

    public static MenuSize sizeOf(int size) {
        if(size >= 0 && size <= 9)
            return ONE_ROW;
        else if (size >= 10 && size <= 18)
            return TWO_ROWS;
        else if (size >= 19 && size <= 27)
            return THREE_ROWS;
        else if (size >= 28 && size <= 36)
            return FOUR_ROWS;
        else if (size >= 37 && size <= 45)
            return FIVE_ROWS;
        else if (size >= 46)
            return SIX_ROWS;
        return null;
    }

    public static MenuSize byOption(int i) {
        switch (i) {
            case 1:
                return ONE_ROW;
            case 2:
                return TWO_ROWS;
            case 3:
                return THREE_ROWS;
            case 4:
                return FOUR_ROWS;
            case 5:
                return FIVE_ROWS;
            case 6:
                return SIX_ROWS;
            default:
                return THREE_ROWS;
        }
    }

}