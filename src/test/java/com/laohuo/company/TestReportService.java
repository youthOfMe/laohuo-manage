package com.laohuo.company;

import com.laohuo.company.service.impl.MainServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * 测试汇报服务
 */
public class TestReportService {

    @Test
    void testLookReport() throws Exception {
        MainServiceImpl.getInstance().lookReport();
    }
}
