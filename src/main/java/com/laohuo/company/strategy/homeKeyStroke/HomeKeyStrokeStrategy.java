package com.laohuo.company.strategy.homeKeyStroke;

/**
 * 策略者模式
 * 定义Home按键策略
 */
public interface HomeKeyStrokeStrategy {

    /**
     * 按键事件触发策略
     * @param homeKeyStrokeStrategyContext 按键策略环境上下文
     */
    void keyBoardEvent(HomeKeyStrokeStrategyContext homeKeyStrokeStrategyContext) throws Exception;
}
