package main.pattern.enam;

public enum TypeTrend {

  LONG(1),
  SHORT(-1),
  NO_TREND(0);

  private final int responseCode;

  TypeTrend(int responseCode) {
    this.responseCode = responseCode;
  }

  public int getResponseCode() {
    return responseCode;
  }

}
