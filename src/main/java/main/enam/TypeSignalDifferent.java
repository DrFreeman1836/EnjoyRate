package main.enam;

public enum TypeSignalDifferent {

  LOW_LEVEL(200),
  MIDDLE_LEVEL(201),
  HIGH_LEVEL(202),
  NO_PATTERN(404),
  ERROR(400);

  private final int responseCode;

  TypeSignalDifferent(int responseCode) {
    this.responseCode = responseCode;
  }

  public int getResponseCode() {
    return responseCode;
  }
}
