package com.jc.service;

import javax.annotation.PostConstruct;

import org.apache.geode.cache.Region;
import org.gj.demo.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.listener.ContinuousQueryDefinition;
import org.springframework.data.gemfire.listener.ContinuousQueryListener;
import org.springframework.stereotype.Service;

import com.jc.dao.DataImport;

/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 16:05 2018/1/11
 */
@Service
public class AppService {
    @Autowired
    DataImport dataImport;
    public Customer findCustomerById(Long customerId) {
        return dataImport.findCustomerById(customerId);
    }
}
