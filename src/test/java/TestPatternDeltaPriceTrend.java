import main.service.impl.trend.PatternDeltaPriceTrend;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestPatternDeltaPriceTrend {

    @Test
    public void getTrendTest(){
        List<BigDecimal> data = new ArrayList<>();
        data.add(new BigDecimal("0.00005"));
        data.add(new BigDecimal("0.00006"));
        data.add(new BigDecimal("0.00005"));
        data.add(new BigDecimal("0.00003"));
        PatternDeltaPriceTrend trend = new PatternDeltaPriceTrend();
        assertEquals(new BigDecimal("-0.00002").toString(), trend.getTrend(data).toString());

        List<BigDecimal> data2 = new ArrayList<>();
        data2.add(new BigDecimal("0.00005"));
        data2.add(new BigDecimal("0.00007"));
        data2.add(new BigDecimal("0.00006"));
        data2.add(new BigDecimal("0.0001"));
        assertEquals(new BigDecimal("0.00005").toString(), trend.getTrend(data2).toString());

    }

}
