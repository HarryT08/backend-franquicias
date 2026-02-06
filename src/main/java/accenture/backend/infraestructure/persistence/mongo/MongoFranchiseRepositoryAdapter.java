package accenture.backend.infraestructure.persistence.mongo;

import accenture.backend.domain.model.Franchise;
import accenture.backend.domain.ports.FranchiseRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class MongoFranchiseRepositoryAdapter implements FranchiseRepositoryPort {

    private final FranchiseReactiveRepository franchiseReactiveRepository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return franchiseReactiveRepository.save(franchise);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return franchiseReactiveRepository.findById(id);
    }
}
