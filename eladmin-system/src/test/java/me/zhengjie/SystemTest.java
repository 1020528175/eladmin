package me.zhengjie;

import cn.hutool.core.lang.Snowflake;
import me.zhengjie.modules.stock.domain.StockMonitor;
import org.junit.Test;

import javax.lang.model.SourceVersion;
import java.net.CookieHandler;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author master
 * @create 2019-09-10 17:06
 */
public class SystemTest {
    
    @Test
    public void test1(){
        Integer i ;
//        System.out.println("i = " + i);
        //C:\Users\ADMINI~1\AppData\Local\Temp\ == Linux上得到的是/tmp  后面是不带‘/’的 切记
        System.out.println("System.getProperty(\"java.io.tmpdir\") = " + System.getProperty("java.io.tmpdir"));

        //D:\java_work\workspace\example\java\eladmin\eladmin-system ==  模块路径，如果是jar包运行的，那就是jar包所在的路径
        System.out.println("System.getProperty(\"user.dir\") = " + System.getProperty("user.dir"));
    }

    @Test
    public void test2(){
        StockMonitor stockMonitor = new StockMonitor();
        stockMonitor.setCode("111");
        change(stockMonitor);
        System.out.println("stockMonitor = " + stockMonitor.getCode());

        Map map = new HashMap();
        map.put('r',"sd");



        List list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("789");

        list.add(1," 后面元素右移");


        for (int i = 0; i <= list.size()-1; i++) {
            System.out.println(list.get(i));

//            if (i !=1){
//                list.remove(i);
//                System.out.println(list.size());
//                System.out.println("*********************");
//            }


        }
        System.out.println("size="+list.size());
    }

    public void change(StockMonitor stockMonitor){
        stockMonitor.setCode("222");
    }


    @Test
    public void test3(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("i = " + Thread.currentThread().getName());
                int i = 1 / 0;
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("true = " + Thread.currentThread().getName());
//        Collections.synchronizedMap();
    }

    @Test
    public void test4(){
        System.out.println("Long.MAX_VALUE = " + Long.MAX_VALUE);
        Snowflake snowflake = new Snowflake(1L,20L);
        System.out.println("snowflake = " + snowflake.nextId());
        System.out.println("snowflake = " + snowflake.nextId());
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("snowflake = " + snowflake.nextId());
        System.out.println("LocalTime.now() = " + LocalTime.now());
    }
}
