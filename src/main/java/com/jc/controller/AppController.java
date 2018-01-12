package com.jc.controller;

import org.gj.demo.domain.Customer;
import com.jc.service.AppService;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 16:54 2018/1/8
 */
@RestController
@RequestMapping("/geode")
@CrossOrigin
public class AppController {
    @Autowired
    AppService appService;

    @RequestMapping("/find")
    @ResponseBody
    public void findByCustomerId(Region<Long, Customer> customers, Long customerId){
        //appService.findByCustomerId((),2L);
    }

}
