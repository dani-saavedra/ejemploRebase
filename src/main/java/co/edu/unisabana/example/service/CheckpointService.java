package co.edu.unisabana.example.service;

import co.edu.unisabana.example.controller.dto.CheckpointDTO;
import co.edu.unisabana.example.service.exception.MaintenenceDayException;
import co.edu.unisabana.example.service.model.Checkin;
import co.edu.unisabana.example.service.model.Checkout;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
public class CheckpointService {

  private static final int DIA_MANTENIMIENTO = 15;
  private final CheckpointPort checkpointPort;
  private final NotificaCheckin notificacion;

  public CheckpointService(CheckpointPort checkpointPort, NotificaCheckin notificacion) {
    this.checkpointPort = checkpointPort;
    this.notificacion = notificacion;
  }

  public void checkin(CheckpointDTO checkpoint) {
    if (checkpoint.dayOfMonth < 0) {
      throw new IllegalArgumentException("Invalid date " + checkpoint.dayOfMonth);
    }
    if ("2".equals(checkpoint.facility) && checkpoint.dayOfMonth == DIA_MANTENIMIENTO) {
      throw new MaintenenceDayException(checkpoint.dayOfMonth, checkpoint.facility);
    }
    Checkin checkin = new Checkin(checkpoint.facility, checkpoint.driver, checkpoint.dayOfMonth);
    checkpointPort.saveCheckin(checkin);
    notificacion.enviarNotificacion(checkin);
  }

  public void checkout(CheckpointDTO checkpoint) {
    Checkin lastCheckin = checkpointPort.findLastCheckin(checkpoint.driver,
        checkpoint.facility);
    if (lastCheckin == null) {
      throw new IllegalArgumentException("don't exist previously checkin");
    }
    if (checkpoint.dayOfMonth > 31 || checkpoint.dayOfMonth < 0) {
      throw new IllegalArgumentException("Invalid date");
    }
    Checkout checkout = new Checkout(checkpoint.facility, checkpoint.driver,
        checkpoint.dayOfMonth);

    checkpointPort.saveCheckout(checkout);
  }
}