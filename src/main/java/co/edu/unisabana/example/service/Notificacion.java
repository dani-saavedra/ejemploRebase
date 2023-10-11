package co.edu.unisabana.example.service;

import co.edu.unisabana.example.service.model.Checkin;
import org.springframework.stereotype.Service;

@Service
public class Notificacion implements NotificaCheckin{

  @Override
  public boolean enviarNotificacion(Checkin checkin) {
    return false;
  }
}
