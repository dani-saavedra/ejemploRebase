package co.edu.unisabana.example.service.exception;

public class MaintenenceDayException extends RuntimeException {

  public MaintenenceDayException(int dayMonth, String facility) {
    super("Dia no permitido " + dayMonth + " Para ingresar al facility "
        + facility);
  }
}
