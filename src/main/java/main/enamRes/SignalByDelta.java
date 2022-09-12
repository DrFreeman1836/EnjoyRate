package main.enamRes;

public enum SignalByDelta {

  SELL(200),
  BUY(201),
  NO_PATTERN(404),
  ERROR(400);

  private final int responseCode;

  SignalByDelta(int responseCode) {
    this.responseCode = responseCode;
  }

  public int getResponseCode() {
    return responseCode;
  }
}
