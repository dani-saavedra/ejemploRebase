package co.edu.unisabana.example.service;


import co.edu.unisabana.example.service.model.Checkin;
import co.edu.unisabana.example.service.model.Checkout;

public interface CheckpointPort {

  void saveCheckin(Checkin checkin);

  void saveCheckout(Checkout checkpoints);

  Checkin findLastCheckin(String driver, String facility);
}