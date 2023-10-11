package co.edu.unisabana.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CheckpointDTO {

  public String facility;
  public String driver;
  @JsonProperty("day_of_month")
  public int dayOfMonth;
}