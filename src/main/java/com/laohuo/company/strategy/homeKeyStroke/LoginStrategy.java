package com.laohuo.company.strategy.homeKeyStroke;

/**
 * 登录策略
 */
public class LoginStrategy implements HomeKeyStrokeStrategy {


    /**
     * 登录事件
     * @param homeKeyStrokeStrategyContext 按键策略环境上下文
     */
    @Override
    public void keyBoardEvent(HomeKeyStrokeStrategyContext homeKeyStrokeStrategyContext) throws Exception {
        homeKeyStrokeStrategyContext.getHomeService().login();
    }
}
