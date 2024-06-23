package com.laohuo.company.strategy.mainKeyStroke;

/**
 * 返回上一层策略
 */
public class ExitStrokeStrategy implements MainKeyStokeStategy {

    /**
     *
     * @param mainKeyStrokeStrategyContext 按键事件触发策略
     * @throws Exception
     */
    @Override
    public void keyBoardEvent(MainKeyStrokeStrategyContext mainKeyStrokeStrategyContext) throws Exception {
        mainKeyStrokeStrategyContext.getMainService().exit();
    }
}
