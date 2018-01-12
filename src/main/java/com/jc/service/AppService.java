package com.jc.service;

import com.jc.domain.Customer;
import org.apache.geode.cache.Region;
import org.springframework.data.gemfire.listener.ContinuousQueryDefinition;
import org.springframework.stereotype.Service;

/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 16:00 2018/1/11
 */
@Service
public interface AppService {
    ContinuousQueryDefinition expensiveOrdersQuery(Region<Long, Customer> customers, int total);
    Customer findByCustomerId(Region<Long, Customer> customers, Long customerId);

}
