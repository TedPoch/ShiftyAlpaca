package tedpoch.ShiftyAlpaca.service;

import tedpoch.ShiftyAlpaca.repository.AnalyzerEvents;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  private final AnalyzerEvents requests;

  public EventService(AnalyzerEvents requests) {
    this.requests = requests;
  }
}
