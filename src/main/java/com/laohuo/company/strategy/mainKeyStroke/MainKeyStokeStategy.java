package com.laohuo.company.strategy.mainKeyStroke;

/**
 * 策略者模式
 * 定义Main界面按键策略
 */
public interface MainKeyStokeStategy {

    /**
     * 按键事件触发策略
     * @param mainKeyStrokeStrategyContext 按键事件触发策略
     */
    void keyBoardEvent(MainKeyStrokeStrategyContext mainKeyStrokeStrategyContext) throws Exception;
}
