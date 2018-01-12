package com.jc.dao;

import com.jc.domain.Customer;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.listener.ContinuousQueryDefinition;
import org.springframework.data.gemfire.listener.ContinuousQueryListener;
import org.springframework.data.gemfire.listener.ContinuousQueryListenerContainer;
import org.springframework.stereotype.Component;

import static org.springframework.data.gemfire.util.CollectionUtils.asSet;

/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 15:57 2018/1/11
 */
@Component
@ClientCacheApplication(name = "GemFireContinuousQueryClient", subscriptionEnabled = true)
public class DataImport {
    @Bean(name = "Customers")
    ClientRegionFactoryBean<Long, Customer> customersRegion(GemFireCache gemfireCache) {
        ClientRegionFactoryBean<Long, Customer> customers = new ClientRegionFactoryBean<>();
        customers.setCache(gemfireCache);
        customers.setClose(true);
        customers.setShortcut(ClientRegionShortcut.PROXY);
        return customers;
    }

    @Bean
    ContinuousQueryListenerContainer continuousQueryListenerContainer(GemFireCache gemfireCache) {
        Region<Long, Customer> customers = gemfireCache.getRegion("/Customers");
        ContinuousQueryListenerContainer container = new ContinuousQueryListenerContainer();
        container.setCache(gemfireCache);
        container.setQueryListeners(asSet(expensiveOrdersQuery(customers, 20)));
        return container;
    }


    private ContinuousQueryDefinition expensiveOrdersQuery(
            Region<Long, Customer> customers,  int total) {
        String query = String.format("SELECT * FROM /Customers c WHERE c.getId().intValue() > %d", total);
        return new ContinuousQueryDefinition("Expensive Orders", query,
                newQueryListener(customers, "Expensive"));
    }
    private ContinuousQueryListener newQueryListener(Region<Long, Customer> customers, String qualifier) {
        return event -> {
            System.err.printf("new order!");
        };
    }
    private Customer findByCustomerId(Region<Long, Customer> customers, Long customerId) {
        return customers.get(customerId);
    }
}
