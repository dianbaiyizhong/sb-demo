package com.nntk.sb.awesome.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.nntk.sb.awesome.mapper.UserMapper;
import com.nntk.sb.awesome.service.DynamicDataSourceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;


@Service
@Slf4j
public class DynamicDataSourceServiceImpl implements DynamicDataSourceService {


    @Resource
    private DataSource dataSource;
    @Resource
    private DefaultDataSourceCreator dataSourceCreator;


    @Resource
    private UserMapper userMapper;

    @Override
    @Async
    public void test() {

        for (int i = 0; i < 100; i++) {
            i = i % 10;
            try {
                // 切换数据源
                DynamicDataSourceContextHolder.push("name" + i);
                // 获取当前数据源名称
                log.info("当前切换的数据源名称：{}", DynamicDataSourceContextHolder.peek());
                userMapper.selectById(1);
                log.info("success:{}", i);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DynamicDataSourceContextHolder.poll();
            }


        }

    }

    @Override
    public void addDs() {

        for (int i = 0; i < 10; i++) {
            DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
            // 添加数据源信息到核心动态数据源组件
            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            dataSourceProperty.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSourceProperty.setUsername("root");
            dataSourceProperty.setPassword("root");
            if (i % 2 == 0) {
                dataSourceProperty.setUrl("jdbc:mysql://localhost:3306/wsc_admin?characterEncoding=utf8&serverTimezone=UTC");

            } else {
                dataSourceProperty.setUrl("jdbc:mysql://localhost:3306/koki?characterEncoding=utf8&serverTimezone=UTC");
            }
            DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
            ds.addDataSource("name" + i, dataSource);
        }

    }
}
