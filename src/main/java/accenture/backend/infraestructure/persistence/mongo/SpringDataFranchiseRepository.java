package accenture.backend.infraestructure.persistence.mongo;

import accenture.backend.domain.model.Franchise;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataFranchiseRepository extends MongoRepository<Franchise, String> {
}