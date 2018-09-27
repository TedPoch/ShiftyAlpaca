package com.tedpoch.ShiftyAlpaca.service;

import com.tedpoch.ShiftyAlpaca.repository.AnalyzerEvents;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final AnalyzerEvents requests;

    public EventService(AnalyzerEvents requests) {
        this.requests = requests;
    }
}
