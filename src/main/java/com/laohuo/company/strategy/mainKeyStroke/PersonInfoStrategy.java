package com.laohuo.company.strategy.mainKeyStroke;

import com.laohuo.company.service.impl.MainServiceImpl;

/**
 * 查看个人信息策略
 */
public class PersonInfoStrategy implements MainKeyStokeStategy {
    @Override
    public void keyBoardEvent(MainKeyStrokeStrategyContext mainKeyStrokeStrategyContext) throws Exception {
        MainServiceImpl.getInstance().personInfo();
    }
}
