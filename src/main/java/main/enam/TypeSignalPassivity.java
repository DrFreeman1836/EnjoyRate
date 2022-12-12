package main.enam;

public enum TypeSignalPassivity {

  ASK(300),
  BID(301),
  ALL(302),
  NO_PATTERN(404),
  ERROR(400);

  private final int responseCode;

  TypeSignalPassivity(int responseCode) {
    this.responseCode = responseCode;
  }

  public int getResponseCode() {
    return responseCode;
  }

}
