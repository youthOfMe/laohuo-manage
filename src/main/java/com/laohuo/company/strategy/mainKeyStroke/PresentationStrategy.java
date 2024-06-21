package com.laohuo.company.strategy.mainKeyStroke;

import com.laohuo.company.service.impl.MainServiceImpl;

/**
 * 汇报工作的策略
 */
public class PresentationStrategy implements MainKeyStokeStategy {

    /**
     * 监听按键事件
     * @param mainKeyStrokeStrategyContext 按键事件触发策略
     * @throws Exception
     */
    @Override
    public void keyBoardEvent(MainKeyStrokeStrategyContext mainKeyStrokeStrategyContext) throws Exception {
        MainServiceImpl.getInstance().presentation();
    }
}
