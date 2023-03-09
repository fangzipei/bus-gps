package com.zonefun.backend.test;

import com.zonefun.backend.sort.QuickSort;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ZoneTest {

    @Test
    public void testQuickSort() {
        int[] nums = new int[]{2, 54, 1, 5, 6, 74, 2, 124, 634, 6};
        QuickSort.quickSortDesc(nums, 0, nums.length - 1);
        Arrays.stream(nums).forEach(System.out::print);
    }

    @Test
    public void testSplit() {
        String set = "slfjadlkf{}";
        String[] split = set.split("\\{\\}");
        System.out.println(split.length);
    }
}
