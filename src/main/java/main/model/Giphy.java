package main.model;

import lombok.Data;

@Data
public class Giphy {

  private String type;

  private String id;

  private Images images;

  @Data
  public class Images {

    private Original original;

    @Data
    public class Original {

      private String url;
    }
  }


}
