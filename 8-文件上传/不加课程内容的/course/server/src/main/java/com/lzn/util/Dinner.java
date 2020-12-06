package com.lzn.util;

import java.util.*;

public class Dinner {
    public static void main(String[] args) {
        final int RANDOM_SIZE = 1000;
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "食堂");
        map.put(2, "大盘鸡");
        map.put(3, "民勇快餐");
        map.put(4, "外卖");

        // 跑100次统计谁最多
        int[] count = new int[map.size()+1];
        int index = 0;
        int max = Integer.MIN_VALUE;
        int maxIndex = 0;
        for (int i = 0; i < RANDOM_SIZE; i++) {
            index = (int)Math.floor(Math.random() * map.size() + 1);
            count[index]++;

            if(count[index] > max){
                max = count[index];
                maxIndex = index;
            }
        }
        System.out.println(map.get(maxIndex));
    }
}
