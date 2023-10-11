package co.edu.unisabana.example.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.edu.unisabana.example.controller.dto.CheckpointDTO;
import co.edu.unisabana.example.service.model.Checkin;
import co.edu.unisabana.example.service.model.Checkout;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheckpointServiceTest {
  // Given when Then
  // Dado que: Pre condiciones para ejecutar el método,
  // Cuando: Que estas ejecutando?,
  // Entonces: Que estas esperando?

  //Mock = Simular un objeto, para que realmente
  //no llame al otro lugar, sino al objeto
  //simulado y nos de una respuesta
  @InjectMocks
  CheckpointService service;

  @Mock
  CheckpointPort port;

  @Mock NotificaCheckin notificaCheckin;

  //@Test
  void Dado_DiaMesMayorPermitido_Cuando_hagaCheckin_Entonces_throwInvalidDate() {
    //A = Preparación de la data
    CheckpointDTO checkpointDTO = new CheckpointDTO();
    checkpointDTO.dayOfMonth = 32;
    // A= Trabaje!!! ejecute

    // A= Verificación, El corazon a la prueba
    assertThrows(IllegalArgumentException.class, () -> {
      service.checkin(checkpointDTO);
    });
    //Verificaciones de invocación: Verify
    //Verificaciones de resultado: Asserts
  }

  @Test
  void Dado_DiaMesMenorPermitido_Cuando_hagaCheckin_Entonces_throwInvalidDate() {
    CheckpointDTO checkpointDTO = new CheckpointDTO();
    checkpointDTO.dayOfMonth = -1;
    assertThrows(IllegalArgumentException.class, () -> {
      service.checkin(checkpointDTO);
    });
  }

  @Test
  void DadoPrimerDiaPermitido_InfoOk_Cuando_hagaCheckin_Entonces_guardaCheckin() {
    CheckpointDTO dto = new CheckpointDTO();
    dto.dayOfMonth = 1;
    dto.driver = "1";
    dto.facility = "bogota";
    service.checkin(dto);
    Mockito.verify(port).saveCheckin(new Checkin(dto.facility, dto.driver, dto.dayOfMonth));
  }

  @Test
  void Dado_InfoOk_Cuando_hagaCheckin_Entonces_guardaCheckin() {
    CheckpointDTO dto = new CheckpointDTO();
    dto.dayOfMonth = 3;
    dto.driver = "1";
    dto.facility = "bogota";
    service.checkin(dto);
    Mockito.verify(port).saveCheckin(new Checkin(dto.facility, dto.driver, dto.dayOfMonth));
  }

  //dia 15 no pueden entrar los Driver código 3, arroja RuntimeException
  @Test
  void Dado_dia15Facility3_Cuando_hagaCheckin_Entonces_NoPermiteIngresoo() {
    CheckpointDTO dto = new CheckpointDTO();
    dto.dayOfMonth = 15;
    dto.driver = "Daniel";
    dto.facility = "3";
    assertThrows(RuntimeException.class, () -> {
      service.checkin(dto);
    });
  }

  @Test
  void DadoFacility3SinMantemiento_Cuando_hagaCheckin_Entonces_guardaCheckin() {
    CheckpointDTO dto = new CheckpointDTO();
    dto.dayOfMonth = 3;
    dto.driver = "1";
    dto.facility = "3";
    service.checkin(dto);
    Mockito.verify(port).saveCheckin(new Checkin(dto.facility, dto.driver, dto.dayOfMonth));
  }

  @Test
  void Cuando_hagaCheckin_Entonces_guardaCheckinYEnviaNotificacion() {
    CheckpointDTO dto = new CheckpointDTO();
    dto.dayOfMonth = 3;
    dto.driver = "1";
    dto.facility = "3";
    service.checkin(dto);
    Checkin checkin = new Checkin(dto.facility, dto.driver, dto.dayOfMonth);
    Mockito.verify(port).saveCheckin(checkin);
    Mockito.verify(notificaCheckin).enviarNotificacion(checkin);
  }

  @Test
  void Debe_GuardarCheckoutCorrectamente_Cuando_invokeCheckout() {

    //Arrange
    Mockito.when(port.findLastCheckin("daniel", "3"))
        .thenReturn(new Checkin("3", "daniel", 5));
    CheckpointDTO checkout = TestUtils.getCheckpointParaCheckout();

    //act
    service.checkout(checkout);
    Checkout checkpoints = new Checkout("3", "daniel", 20);
    //assert
    Mockito.verify(port).saveCheckout(checkpoints);
  }

  @Test
  void DadoNoExisteCheckin_Cuando_IntenteHacerCheckout_Entonces_ArrojaException() {

    //Arrange
    Mockito.when(port.findLastCheckin("daniel", "3"))
        .thenReturn(null);
    CheckpointDTO checkout = TestUtils.getCheckpointParaCheckout();

    //act & assert
    assertThrows(IllegalArgumentException.class, () -> {
      service.checkout(checkout);
    });
  }


}
