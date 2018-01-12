package com.jc.service;

import com.jc.dao.DataImport;
import org.apache.geode.cache.Region;
import org.gj.demo.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.listener.ContinuousQueryDefinition;
import org.springframework.data.gemfire.listener.ContinuousQueryListener;
import org.springframework.stereotype.Service;

/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 16:05 2018/1/11
 */
@Service
public class AppService {
    @Autowired
    DataImport dataImport;
    public ContinuousQueryDefinition expensiveOrdersQuery(Region<Long, Customer> customers, int total) {
        String query = String.format("SELECT * FROM /Customers c WHERE c.getId().intValue() > %d", total);
        return new ContinuousQueryDefinition("Expensive Orders", query,
                newQueryListener(customers, "Expensive"));
    }
    private ContinuousQueryListener newQueryListener(Region<Long, Customer> customers, String qualifier) {
        return event -> {
            System.err.printf("new order!");
        };
    }
    public Customer findByCustomerId(Region<Long, Customer> customers, Long customerId) {
        return customers.get(customerId);
    }
}
