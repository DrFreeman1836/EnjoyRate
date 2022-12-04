package main.enam;

public enum TypeSignal {

  BUY(200),
  SELL(201),
  ALL(202),
  NO_PATTERN(404),
  ERROR(400);

  private final int responseCode;

  TypeSignal(int responseCode) {
    this.responseCode = responseCode;
  }

  public int getResponseCode() {
    return responseCode;
  }

}
