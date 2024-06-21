package com.laohuo.company.strategy.mainKeyStroke;

/**
 * 查看汇报的按键策略
 */
public class LookReportStrategy implements MainKeyStokeStategy {

    /**
     * 按键策略
     * @param mainKeyStrokeStrategyContext 按键事件触发策略
     * @throws Exception
     */
    @Override
    public void keyBoardEvent(MainKeyStrokeStrategyContext mainKeyStrokeStrategyContext) throws Exception {
        mainKeyStrokeStrategyContext.getMainService().lookReport();
    }
}
