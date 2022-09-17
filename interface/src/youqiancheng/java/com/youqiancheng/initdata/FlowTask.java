package com.youqiancheng.initdata;

import com.youqiancheng.service.manager.service.shopmanagement.ShopManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FlowTask {
    private static final Logger logger = LoggerFactory.getLogger(FlowTask.class);
    @Autowired
    private ShopManagementService shopManagementService;
    @Scheduled(cron = "0 50 23 * * ? ")
    public void certificateExpiredTask() {
        logger.info("-----定期保存收益流水!!!");
        shopManagementService.saveFlow();
        logger.info("-----定期保存收益流水!!!");
    }
}
