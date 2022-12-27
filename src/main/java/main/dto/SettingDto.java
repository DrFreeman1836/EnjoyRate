package main.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SettingDto {

  private String pattern;

  private String name;

  private Number value;

}
