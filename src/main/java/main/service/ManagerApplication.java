package main.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import main.model.DataGiphy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ManagerApplication {

  private final ClientOpenExchangeRates clientOpenExchangeRates;

  private final ClientGiphy clientGiphy;

  private final GregorianCalendar calendar;

  private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @Value("${urlopenexchangerates.app_id}")
  private String appId;

  @Value("${urlopenexchangerates.symbols}")
  private String symbols;

  @Value("${giphy.api_key}")
  private String apiKey;

  @Autowired
  public ManagerApplication(ClientOpenExchangeRates client, ClientGiphy clientGiphy) {
    this.clientOpenExchangeRates = client;
    this.clientGiphy = clientGiphy;
    calendar = new GregorianCalendar();
  }

  public double getRateDifference() throws Exception {
    String yesterdayDate = getDate();
    double yesterday = clientOpenExchangeRates.getQuotationHistorical(yesterdayDate, appId, symbols).getRates().get(symbols);
    double today = clientOpenExchangeRates.getQuotation(appId, symbols).getRates().get(symbols);
    return today - yesterday;
  }

  public String getGiphy(String request) throws Exception {
    DataGiphy listGiphy = clientGiphy.getListGiphy(apiKey, request);
    int size = listGiphy.getData().size();
    return listGiphy.getData().get(rnd(0, size - 1)).getImages().getOriginal().getUrl();
  }

  public String getDate() {
    calendar.add(Calendar.DATE, -1);
    return dateFormat.format(calendar.getTime());
  }

  public int rnd(int min, int max) {
    Random rnd = new Random();
    int number = min + rnd.nextInt(max - min + 1);
    return number;
  }

}
