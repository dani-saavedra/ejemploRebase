package co.edu.unisabana.example.controller;


import co.edu.unisabana.example.controller.dto.CheckpointDTO;
import co.edu.unisabana.example.controller.dto.ResponseGate;
import co.edu.unisabana.example.service.CheckpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Explicar OpenAPI

@RestController
public class GateController {

  Logger logger = LoggerFactory.getLogger(GateController.class);

  private final CheckpointService checkpointService;

  public GateController(CheckpointService checkpointService) {
    this.checkpointService = checkpointService;
  }

  @PostMapping("/checkpoint/checkin")
  public ResponseGate checkin(@RequestBody CheckpointDTO checkpoint) {
    checkpointService.checkin(checkpoint);
    return new ResponseGate(true);

  }

}