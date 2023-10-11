package co.edu.unisabana.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PruebaController {

  IService prueba;

  public PruebaController(IService prueba) {
    this.prueba = prueba;
  }

  @GetMapping(path = "/hola")
  public String hola() {
    return prueba.saludo();
  }
}
