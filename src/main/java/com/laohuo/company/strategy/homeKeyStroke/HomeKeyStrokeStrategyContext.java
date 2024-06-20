package com.laohuo.company.strategy.homeKeyStroke;

import com.laohuo.company.common.KeyBoardEvent;
import com.laohuo.company.service.HomeService;
import com.laohuo.company.service.impl.HomeServiceImpl;
import lombok.Data;

/**
 * 策略环境变量
 */
@Data
public class HomeKeyStrokeStrategyContext {
    private KeyBoardEvent keyBoardEvent;

    private HomeService homeService = HomeServiceImpl.getInstance();
}
