package accenture.backend.domain.ports;

import accenture.backend.domain.model.Franchise;

import java.util.Optional;

public interface FranchiseRepositoryPort {

    Franchise save(Franchise franchise);

    Optional<Franchise> findById(String id);
}
