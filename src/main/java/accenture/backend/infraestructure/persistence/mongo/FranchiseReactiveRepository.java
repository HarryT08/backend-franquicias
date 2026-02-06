package accenture.backend.infraestructure.persistence.mongo;

import accenture.backend.domain.model.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranchiseReactiveRepository extends ReactiveMongoRepository<Franchise, String> {
}
