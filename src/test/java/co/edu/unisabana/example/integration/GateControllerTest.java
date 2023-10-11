package co.edu.unisabana.example.integration;

import co.edu.unisabana.example.controller.dto.CheckpointDTO;
import co.edu.unisabana.example.controller.dto.ResponseGate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GateControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void checkin() {
    CheckpointDTO checkpoint = new CheckpointDTO("1", "2", 3);
    ResponseEntity<ResponseGate> responseGateResponseEntity = restTemplate.postForEntity(
        "/checkpoint/checkin", checkpoint, ResponseGate.class);
    Assertions.assertTrue(responseGateResponseEntity.getBody().transactionResult);
  }

}
