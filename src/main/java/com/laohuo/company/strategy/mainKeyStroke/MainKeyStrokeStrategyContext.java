package com.laohuo.company.strategy.mainKeyStroke;


import com.laohuo.company.common.KeyBoardEvent;
import com.laohuo.company.service.MainService;
import com.laohuo.company.service.impl.MainServiceImpl;
import lombok.Data;

/**
 * 策略环境变量
 */
@Data
public class MainKeyStrokeStrategyContext {
    private KeyBoardEvent keyBoardEvent;

    private MainService mainService = MainServiceImpl.getInstance();
}
