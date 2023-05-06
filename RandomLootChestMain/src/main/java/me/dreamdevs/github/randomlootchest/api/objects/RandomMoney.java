package me.dreamdevs.github.randomlootchest.api.objects;

import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
public class RandomMoney {

    private int min;
    private int max;

    public RandomMoney(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMoney() {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

}