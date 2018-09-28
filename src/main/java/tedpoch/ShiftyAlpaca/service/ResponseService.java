package tedpoch.ShiftyAlpaca.service;

import tedpoch.ShiftyAlpaca.repository.AnalyzerResponses;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
  private final AnalyzerResponses responses;

  public ResponseService(AnalyzerResponses responses) {
    this.responses = responses;
  }
}
