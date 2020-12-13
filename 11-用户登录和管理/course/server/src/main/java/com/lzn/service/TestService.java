package com.lzn.service;

import com.lzn.domain.Test;
import com.lzn.domain.TestExample;
import com.lzn.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;


    public List<Test> list() {
//        return testMapper.list();
        TestExample testExample = new TestExample();

        // createCriteria才是相当于where
        testExample.createCriteria().andIdEqualTo("1");
//        testExample.setOrderByClause("id asc");// id 从小到大
        testExample.setOrderByClause("id desc");// id 从大到小
        return testMapper.selectByExample(testExample);
    }
}
