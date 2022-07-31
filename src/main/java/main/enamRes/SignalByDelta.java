package main.enamRes;

public enum SignalByDelta {

  ASK(200),
  BID(201),
  ALL(202),
  NO_PATTERN(404);

  private final int responseCode;

  SignalByDelta(int responseCode) {
    this.responseCode = responseCode;
  }

  public int getResponseCode() {
    return responseCode;
  }
}
