package com.zonefun.backend.sort;

public class QuickSort {
    /**
     * @description 数字类型简单快排（升序）
     * @param nums int[] 数字数组
     * @param low int 低位索引
     * @param high int 高位索引
     * @return void
     * @date 2022/2/11 16:05
     * @author ZoneFang
     */
    public static void quickSort(int[] nums, int low, int high) {
        if(low >= high) return;
        int i = low; // 记录动态的从左出发的索引位置
        int j = high; // 记录动态的从右出发的索引位置
        int value = nums[low]; // 记录当前中间值
        while (i < j) {
            // 从后往前找比中间值小的
            while (j > i && nums[j] >= value) {
                j--;
            }
            // 如果找到了且此时还比i大 则进行交换
            if(i < j){
                nums[i] = nums[j];
                nums[j] = value;
                // 交换完毕后因为i的位置已经变为此时j的值
                // 所以需要++
                i++;
            }
            /* 如果在i右边没找到比中间值大的说明右边不需要进行排序
               或者找到了比中间值大的，在交换后则需要从左边进行上面相同的步骤
               正是因为这个步骤才能保证最后比中间值大/小的值分布在中间值的右/左边
                    假如只是从右边进行递减 进行j--的话
                    [5,2,3,7,4,1] -> [1,2,3,7,4,5] -> [1,2,3,5,4,7]
                    会发现中间 数值呈现为‘\/’形状的地方谷底被略过了（此处为4）*/
            while (i < j && nums[i] <= value){
                i++;
            }
            if(i < j){
                nums[j] = nums[i];
                nums[i] = value;
                j--;
            }
        }
        quickSort(nums, low, i - 1);
        quickSort(nums, j + 1, high);
    }

    /**
     * @description 数字类型简单快排（降序）
     * @param nums int[] 数字数组
     * @param low int 低位索引
     * @param high int 高位索引
     * @return void
     * @date 2022/2/11 16:34
     * @author ZoneFang
     */
    public static void quickSortDesc(int[] nums, int low, int high) {
        if(low >= high) return;
        int i = low; // 记录动态的从左出发的索引位置
        int j = high; // 记录动态的从右出发的索引位置
        int value = nums[low]; // 记录当前中间值
        while (i < j) {
            // 从后往前找比中间值大的
            while (j > i && nums[j] <= value) {
                j--;
            }
            // 如果找到了且此时还比i大 则进行交换
            if(i < j){
                nums[i] = nums[j];
                nums[j] = value;
                // 交换完毕后因为i的位置已经变为此时j的值
                // 所以需要++
                i++;
            }
            /* 如果在i右边没找到比中间值大的说明右边不需要进行排序
               或者找到了比中间值大的，在交换后则需要从左边进行上面相同的步骤 */
            while (i < j && nums[i] >= value){
                i++;
            }
            if(i < j){
                nums[j] = nums[i];
                nums[i] = value;
                j--;
            }
        }
        quickSortDesc(nums, low, i - 1);
        quickSortDesc(nums, j + 1, high);
    }
}