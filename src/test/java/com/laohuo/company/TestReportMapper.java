package com.laohuo.company;

import com.laohuo.company.dao.ReportMapper;
import org.junit.jupiter.api.Test;

/**
 * 测试汇报的单元测试
 */
public class TestReportMapper {

    @Test
    void addPresentation() throws Exception {
        ReportMapper.addPresentation("哈哈哈", "这是我的汇报");
    }
}
