package com.laohuo.company.service;

/**
 * 主页面服务层
 */
public interface MainService {

    /**
     * 查看个人信息
     */
    void personInfo() throws Exception;

    /**
     * 修改密码
     * @throws Exception
     */
    void updatePassword() throws Exception;

    /**
     * 汇报工作
     * @throws Exception
     */
    void presentation() throws Exception;

    /**
     * 查看汇报
     * @throws Exception
     */
    void lookReport() throws Exception;
}
