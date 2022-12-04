package main.enam;

public enum TypeSelection {

  ASK(200),
  BID(201),
  ALL(202),
  NO_PATTERN(404),
  ERROR(400);

  private final int responseCode;

  TypeSelection(int responseCode) {
    this.responseCode = responseCode;
  }

  public int getResponseCode() {
    return responseCode;
  }
}
