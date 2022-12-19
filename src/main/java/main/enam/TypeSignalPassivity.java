package main.enam;

public enum TypeSignalPassivity {

  YES_PATTERN(300),
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
