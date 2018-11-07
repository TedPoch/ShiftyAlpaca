package ShiftyAlpaca.model;

/** This interface allows us to create a return type in the
    AnalyzerController.java returnResult() method. I can now
    implement the interface in my VerificationResponse and
    QueryResponse classes and return either of them in
    the controller method's switch statement.
 */
public class Response {
  private String id;
}
