package main.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TICK")
public class Tick {

  /**
   * id тика
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private int id;

  /**
   * цена Ask
   */
  @Column(columnDefinition = "decimal(7,5)", name = "PRICE_ASK")
  private BigDecimal priceAsk;

  /**
   * цена Bid
   */
  @Column(columnDefinition = "decimal(7,5)", name = "PRICE_BID")
  private BigDecimal priceBid;

  /**
   * время тика в мс
   */
  @Column(name = "TIMESTAMP")
  private long timestamp;

  /**
   * флаг порога жабы
   */
  @Column(name = "FLAG_FROG")
  private boolean flagFrog;

  @Override
  public String toString() {
    return "Tick{" +
        "id=" + id +
        ", priceAsk=" + priceAsk +
        ", priceBid=" + priceBid +
        ", timestamp=" + timestamp +
        ", flagFrog=" + flagFrog +
        '}';
  }
}
