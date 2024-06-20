package com.laohuo.company.strategy.homeKeyStroke;

/**
 * 作者：盛展
 * 描述：牛逼
 */
public class RegisterStrategy implements HomeKeyStrokeStrategy {

    /**
     * 编写策略
     * @param homeKeyStrokeStrategyContext
     */
    @Override
    public void keyBoardEvent(HomeKeyStrokeStrategyContext homeKeyStrokeStrategyContext) throws Exception {
        homeKeyStrokeStrategyContext.getHomeService().register();
    }
}
