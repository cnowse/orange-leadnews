package com.orange.wemedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.orange.model.common.dto.ResponseResult;

@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class ResultTest {

    @Test
    public void result() {
        List<String> strings = new ArrayList<>();
        strings.add("xiaoming");
        strings.add("lisi");
        strings.add("wangwu");
        String collect = strings.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println("collect = " + collect);
        ResponseResult result = new ResponseResult(1L, 5L, 11L, strings);
        System.out.println("result = " + JSON.toJSONString(result));
    }

    @Test
    public void name() {
        String str = "123,abc,add";
        String[] split = str.split(",", 1);
        System.out.println("split = " + Arrays.toString(split));
    }
}
