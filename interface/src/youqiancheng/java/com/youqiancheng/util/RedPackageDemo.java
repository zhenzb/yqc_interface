package com.youqiancheng.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RedPackageDemo{

    private static double moneyNumer = 50; //红包总额度
    private static int num = 4; //多少份/多少人抢


    //main
    public static void main(String[] args) {

        for(int i = 0;i<5;i++){
            splitRedPackage(100,20);
        }

    }
    public static  List<Double> splitRedPackage(double moneyP,int numP){
        double money = moneyP;  //单位（元）
        int num = numP;
        System.out.println("总金额："+money+"元\t 拆分红包总个数:" + num);
        Integer[] array = divived(money,num);
        shuffle(array);
        System.out.println("拆分后的各个红包金额数如下:");
        List<Double> newArray=new ArrayList<>();
        for (int i:array){
            newArray.add((double) i/100);
        }
        for (Double aDouble : newArray) {
            System.out.print(aDouble+"元\t");
        }
        System.out.println("\n=================================");
        return newArray;
    }


    //全局rand对象
    private static Random rand = new Random();

    //交换方法
    public static <T> void swap(T[] a, int i, int j){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    //打乱数组 产生随机性
    public static <T> void shuffle(T[] arr) {
        int length = arr.length;
        for ( int i = length; i > 0; i-- ){
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }


    /**
     * 抢红包操作
     * @param moneyP 总金额
     * @param numP 份数
     */
    public static  void doIt1(double moneyP,int numP){
        //循环测试4遍 每组不一样
        double money = moneyP;  //单位（元）
        int num = numP;
        System.out.println("总金额："+money+"元\t 拆分红包总个数:" + num);
        Integer[] array = divived(money,num);
        shuffle(array);
        System.out.println("拆分后的各个红包金额数如下:");
        for (int i:array){
            System.out.print((double) i/100+"元\t");
        }
        System.out.println("\n=================================");
//
//        System.out.println("用户正在抢，当前有"+num+"个用户在抢！");
//        shuffle(array);
//        for(int i = 0 ;i<num;i++){
//            System.out.println("第"+(i+1)+"个用户抢到了:"+(double)array[i]/100+"元");
//        }
//        System.out.println("\n——————————————————————————————————");
    }


    /****
     * 红包拆分方法
     * @param money  被拆分的总金额 (单位元)
     * @param n    被拆分的红包个数
     * @return  拆分后的每个红包金额数组
     */
    public static Integer[] divived(double money, int n){

        int fen = (int) (money*100);
        if(fen < n || fen < 1){
            throw new IllegalArgumentException("被拆分的总金额不能小于1分");
        }
        // 创建一个长度等于n的红包数组
        Integer[] array = new Integer[n];
        //第一步 每个红包先塞1分
        Arrays.fill(array,1);
        //总金额减去已分配的n 分钱
        fen -= n;
        //第二步，循环遍历如果剩余金额>0 则一直分配
        int i = 0;  //从第一个红包进行分配

        //创建一个随机分配对象
        Random random = new Random();
        while (fen > 1){
            int f  = random.nextInt(fen);  //创建范围[0,fen)
            array[i++%n] +=  f;
            fen -= f;
        }
        //判断剩余未分配的金额是否大于0,如果大于0，可以把剩下未分配金额塞到第一个红包中
        if (fen > 0){
            array[0] +=  fen;
        }
        return array;
    }


}

