package me.zhengjie;

import org.junit.Test;

/**
 * @author master
 * @create 2019-09-10 17:06
 */
public class SystemTest {
    
    @Test
    public void test1(){
        //C:\Users\ADMINI~1\AppData\Local\Temp\ == Linux上得到的是/tmp  后面是不带‘/’的 切记
        System.out.println("System.getProperty(\"java.io.tmpdir\") = " + System.getProperty("java.io.tmpdir"));

        //D:\java_work\workspace\example\java\eladmin\eladmin-system ==  模块路径，如果是jar包运行的，那就是jar包所在的路径
        System.out.println("System.getProperty(\"user.dir\") = " + System.getProperty("user.dir"));
    }
}
