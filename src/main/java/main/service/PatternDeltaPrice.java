package main.service;

import main.model.Tick;
import main.service.impl.TickManagerServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatternDeltaPrice {

    private int response;

    private List<Tick> listTicks;

    private TickManagerServiceImpl tickManagerService;

    public int getResponse() {
        //
        return response;
    }
}
