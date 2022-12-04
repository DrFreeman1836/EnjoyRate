package main.service.impl.trend;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class Trend {


    public BigDecimal getTrend(List<BigDecimal> listTicks){
        BigDecimal result = new BigDecimal(0);
        for(int i = 1; i < listTicks.size(); i++){
            result = result.add(listTicks.get(i).subtract(listTicks.get(i-1)));
        }
        return result;
    }

}
