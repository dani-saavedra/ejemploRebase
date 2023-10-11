package co.edu.unisabana.example.repository;

import co.edu.unisabana.example.repository.entity.CheckpointEntity;
import co.edu.unisabana.example.repository.jpa.CheckpointRepository;
import co.edu.unisabana.example.service.CheckpointPort;
import co.edu.unisabana.example.service.model.Checkin;
import co.edu.unisabana.example.service.model.Checkout;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CheckpointDAO implements CheckpointPort {

  private CheckpointRepository checkpointRepository;

  @Override
  public void saveCheckin(Checkin checkin) {
    checkpointRepository.save(CheckpointEntity.fromCheckin(checkin));
  }

  @Override
  public void saveCheckout(Checkout checkout) {
    checkpointRepository.save(CheckpointEntity.fromCheckout(checkout));
  }

  @Override
  public Checkin findLastCheckin(String driver, String facility) {
    Optional<CheckpointEntity> result = checkpointRepository.findFirstByDriverAndFacilityAndFinalizedIsFalse(
        driver,
        facility);
    return result.map(CheckpointEntity::toCheckin).orElse(null);
  }
}
