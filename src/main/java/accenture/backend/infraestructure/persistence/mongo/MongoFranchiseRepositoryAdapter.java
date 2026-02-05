package accenture.backend.infraestructure.persistence.mongo;

import accenture.backend.domain.model.Franchise;
import accenture.backend.domain.ports.FranchiseRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MongoFranchiseRepositoryAdapter implements FranchiseRepositoryPort {

    private final SpringDataFranchiseRepository springDataFranchiseRepository;

    @Override
    public Franchise save(Franchise franchise) {
        return springDataFranchiseRepository.save(franchise);
    }

    @Override
    public Optional<Franchise> findById(String id) {
        return springDataFranchiseRepository.findById(id);
    }
}
