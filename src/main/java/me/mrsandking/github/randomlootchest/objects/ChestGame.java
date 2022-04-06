package me.mrsandking.github.randomlootchest.objects;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ChestGame {

    private String id;
    private String title;
    private int time;
    private int maxItems;
    private int maxItemsInTheSameType;
    private List<RandomItem> items = new ArrayList<>();

    public ChestGame(String id) {
        this.id = id;
    }

}