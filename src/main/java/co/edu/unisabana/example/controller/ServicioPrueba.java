package co.edu.unisabana.example.controller;

import org.springframework.stereotype.Service;

@Service(value = "x")
public class ServicioPrueba implements IService {

  public String saludo() {
    return "Hola Jairo";
  }
}
