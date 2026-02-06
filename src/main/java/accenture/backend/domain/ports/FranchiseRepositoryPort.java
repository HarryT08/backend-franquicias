package accenture.backend.domain.ports;

import accenture.backend.domain.model.Franchise;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface FranchiseRepositoryPort {

    Mono<Franchise> save(Franchise franchise);

    Mono<Franchise> findById(String id);
}
