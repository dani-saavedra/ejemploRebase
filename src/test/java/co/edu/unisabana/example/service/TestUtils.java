package co.edu.unisabana.example.service;

import co.edu.unisabana.example.controller.dto.CheckpointDTO;

public class TestUtils {

  public static CheckpointDTO getCheckpointParaCheckout() {
    CheckpointDTO checkout = new CheckpointDTO();
    checkout.dayOfMonth = 20;
    checkout.facility = "3";
    checkout.driver = "daniel";
    return checkout;
  }
}
