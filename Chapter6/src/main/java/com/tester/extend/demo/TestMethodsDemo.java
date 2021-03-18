package com.tester.extend.demo;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestMethodsDemo {

    @Test
    public void test1(){
        Assert.assertEquals(1,2);
        System.out.println("Failed");
    }

    @Test
    public void test2(){
        Assert.assertEquals(1,1);
        System.out.println("Successed");
    }

    @Test
    public void test3(){
        Assert.assertEquals("aaa","aaa");
        System.out.println("Successed2");
    }

    @Test
    public void logDemo(){
        Reporter.log("这是我们自己写的日志");
        System.out.println("Testlog");
        throw new RuntimeException("这是我自己的运行时异常");

    }
}
