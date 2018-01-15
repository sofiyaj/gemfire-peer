/*
 *  Copyright 2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jc;

import org.apache.geode.cache.query.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.PeerCacheApplication;
import org.springframework.data.gemfire.listener.ContinuousQueryDefinition;
import org.springframework.data.gemfire.listener.ContinuousQueryListener;
import org.springframework.data.gemfire.listener.ContinuousQueryListenerContainer;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.gj.demo.domain.Customer;
import static org.springframework.data.gemfire.util.CollectionUtils.asSet;
/**
 *@Description:
 *@Date: 16:52 \
 *@params:
 *@return:
*/
@SpringBootApplication
@ClientCacheApplication(name = "GemFireContinuousQueryClient", subscriptionEnabled = true)
@SuppressWarnings("unused")
public class Application {
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
		//container.setQueryListeners(asSet(expensiveOrdersQuery(customers, 20)));
		container.setQueryListeners(asSet(ageQueryDefinition(customers, 15,35)));
		return container;
	}

	private ContinuousQueryDefinition expensiveOrdersQuery(Region<Long, Customer> customers, int total) {
		String query = String.format("SELECT * FROM /Customers c WHERE c.getId().intValue() > %d", total);
		return new ContinuousQueryDefinition("Expensive Orders", query, newQueryListener(customers, "Expensive"));
	}

	private ContinuousQueryDefinition ageQueryDefinition(Region<Long, Customer> customers, int ageFrom,int ageTo){
		String query = String.format("SELECT * FROM /Customers c WHERE c.getAge().intValue() > %d AND c.getAge().intValue() < %d ", ageFrom,ageTo);
		return new ContinuousQueryDefinition("Young Query ",query,newQueryListener(customers,"Young Query"));
	}

	private ContinuousQueryListener newQueryListener(Region<Long, Customer> customers, String qualifier) {
		return event -> {
			System.err.printf("new order!" + event.toString());
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
