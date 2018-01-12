package com.jc.dao;

import org.gj.demo.domain.Customer;
import org.apache.geode.cache.Region;
import org.springframework.stereotype.Component;



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
    public Customer findCustomerById(Long customerId) {
        return customers.get(customerId);
    }
}
