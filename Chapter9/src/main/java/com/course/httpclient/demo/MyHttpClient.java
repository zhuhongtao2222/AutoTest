package com.course.httpclient.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class MyHttpClient {

    @Test
    public void test1() throws IOException {
        //存放返回的结果
        String result;
        HttpGet get = new HttpGet("http://www.baidu.com");
        //用来执行get方法的
        //HttpClient client = new DefaultHttpClient();
        //HttpResponse response = client.execute(get);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }
}
