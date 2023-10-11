package co.edu.unisabana.example.ejemplo.refleccion;

import java.lang.reflect.Constructor;

interface Servicio {
  void realizarAccion();
}

class ServicioImplementacionA implements Servicio {

  @Override
  public void realizarAccion() {
    System.out.println("Realizando la acción desde ServicioImplementacionA");
  }
}

class ServicioImplementacionB implements Servicio {

  @Override
  public void realizarAccion() {
    System.out.println("Realizando la acción desde ServicioImplementacionB");
  }
}


class Cliente {

  private Servicio servicio;

  public Cliente(Servicio servicio) {
    this.servicio = servicio;
  }

  public void realizarOperacion() {
    servicio.realizarAccion();
  }
}

public class EjemploInyeccionDependencias {

  public static void main(String[] args) throws Exception {
    //Ioc Container
    // Crear una instancia de Cliente utilizando reflexión para inyectar una implementación específica de Servicio
    Class<?> servicioClase = ServicioImplementacionA.class;
    Class<?> clienteClase = Cliente.class;
    // Obtener el constructor de Cliente que toma un parámetro de tipo Servicio
    Constructor<?> constructor = clienteClase.getDeclaredConstructor(Servicio.class);

    // Crear una instancia de la implementación de Servicio que deseamos utilizar
    Servicio servicio = (Servicio) servicioClase.newInstance();

    // Utilizar el constructor de Cliente para crear una instancia de Cliente
    Cliente cliente = (Cliente) constructor.newInstance(servicio);
    //fin ioc container

    // Utilizar el cliente
    cliente.realizarOperacion();
  }
}
