package com.youqiancheng.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class RandomSplit {
    public static void main(String[] args) {
        // 测试代码
        ArrayList<BigDecimal> list = getRedPackage(10000, 20);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }



    public static ArrayList<BigDecimal> getRedPackage(double money, int count){
        ArrayList<BigDecimal> list=new ArrayList<>();
        ArrayList<Integer> integers = randomDivide(money, count);
        for (int i = 0; i < integers.size(); i++) {
            list.add(BigDecimal.valueOf(integers.get(i)).divide(BigDecimal.valueOf(100)));
        }
        return list;
    }


    /****
     * 红包拆分方法
     * @param money  被拆分的总金额 (单位元)
     * @param count      被拆分的红包个数
     * @return 拆分后的每个红包金额数组
     */


    public static ArrayList<Integer> randomDivide(double money, int count) {

        // 创建一个长度的红包数组
        ArrayList<Integer> redList = new ArrayList<>();

        // 由于double的精度分体将其转换为int计算, 即将元转换为分计算，红包最小单位以分计算
        int totalMoney = (int) (money * 100);

//        // 判断红包的总金额
//        if (money > 200) {
//            System.out.println("单个红包不能超过200元");
//            return redList; // 返回空的红包集合
//        }
        if (totalMoney < count || totalMoney < 1) {
            System.out.println("被拆分的总金额不能小于0.01元");
            return redList; // 返回空的红包集合
        }

        //2. 进行随机分配
        Random rand = new Random();

        int leftMoney = totalMoney;
        int leftCount = count;
        // 随机分配公式：1 + rand.nextInt(leftMoney / leftCount * 2);
        for (int i = 0; i < count - 1; i++) {
            int money_ = 1 + rand.nextInt(leftMoney / leftCount * 2);
            redList.add(money_);
            leftMoney -= money_;
            leftCount--;
        }
        // 把剩余的最后一个放到最后一个包里
        redList.add(leftMoney);
        return redList;
    }

    public static ArrayList<Integer> averageDivide(double money, int count) {
        // 创建一个长度的红包数组
        ArrayList<Integer> redList = new ArrayList<>();

        // 由于double的精度分体将其转换为int计算, 即将元转换为分计算，红包最小单位以分计算
        int totalMoney = (int) (money * 100);

        int avg = totalMoney / count;
        int mod = totalMoney % count;

        for (int i = 0; i < count - 1; i++) {
            redList.add(avg);
        }
        redList.add(avg + mod);
        return redList;
    }
}
