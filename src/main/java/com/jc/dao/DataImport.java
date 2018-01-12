package com.jc.dao;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.gj.demo.domain.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.listener.ContinuousQueryDefinition;
import org.springframework.data.gemfire.listener.ContinuousQueryListener;
import org.springframework.data.gemfire.listener.ContinuousQueryListenerContainer;
import org.springframework.stereotype.Component;

import static org.springframework.data.gemfire.util.CollectionUtils.asSet;

import javax.annotation.Resource;

/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 15:57 2018/1/11
 */
@Component
public class DataImport {
	@Resource(name = "Customers")
    private Region<Long, Customer> customers;   
    public Customer findCustomerById( Long customerId) {
        return customers.get(customerId);
    }
}
