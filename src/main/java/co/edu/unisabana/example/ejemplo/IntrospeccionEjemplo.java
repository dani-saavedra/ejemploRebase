package co.edu.unisabana.example.ejemplo;

import java.lang.reflect.Field;

public class IntrospeccionEjemplo {

  public static void main(String[] args) {
    Class<?> clasePersona = Persona.class;
    Field[] campos = clasePersona.getDeclaredFields();
    for (Field campo : campos) {
      System.out.println("Campo: " + campo.getName());
    }
  }
}

class Persona {

  private String nombre;
  private int edad;
}