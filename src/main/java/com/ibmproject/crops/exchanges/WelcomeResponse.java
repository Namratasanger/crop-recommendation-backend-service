package com.ibmproject.crops.exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WelcomeResponse {
  private String name;
  private String message;
}
