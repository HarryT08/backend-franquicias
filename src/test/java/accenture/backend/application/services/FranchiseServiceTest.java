package accenture.backend.application.services;

import accenture.backend.domain.model.Branch;
import accenture.backend.domain.model.Franchise;
import accenture.backend.domain.model.Product;
import accenture.backend.domain.ports.FranchiseRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class FranchiseServiceTest {
    private FranchiseRepositoryPort repository;
    private FranchiseService service;

    @BeforeEach
    void setUp() {
        repository = mock(FranchiseRepositoryPort.class);
        service = new FranchiseService(repository);
    }

    @Test
    void shouldCreateFranchise() {
        when(repository.save(any()))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.createFranchise("Test Franchise"))
                .expectNextMatches(f ->
                        f.getName().equals("Test Franchise") &&
                                f.getBranches().isEmpty()
                )
                .verifyComplete();
    }

    @Test
    void shouldAddBranchToFranchise() {
        Franchise franchise = Franchise.builder()
                .id("1")
                .name("Franchise")
                .branches(new ArrayList<>())
                .build();

        when(repository.findById("1"))
                .thenReturn(Mono.just(franchise));

        when(repository.save(any()))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.addBranchToFranchise("1", "Main Branch"))
                .expectNextMatches(f ->
                        f.getBranches().size() == 1 &&
                                f.getBranches().get(0).getName().equals("Main Branch")
                )
                .verifyComplete();
    }

    @Test
    void shouldGetProductWithMaxStock() {
        Product p1 = Product.builder()
                .id("p1")
                .name("A")
                .stock(10L)
                .build();

        Product p2 = Product.builder()
                .id("p2")
                .name("B")
                .stock(50L)
                .build();

        Branch branch = Branch.builder()
                .id("b1")
                .name("Branch")
                .products(List.of(p1, p2))
                .build();

        Franchise franchise = Franchise.builder()
                .id("1")
                .name("Franchise")
                .branches(List.of(branch))
                .build();

        when(repository.findById("1"))
                .thenReturn(Mono.just(franchise));

        StepVerifier.create(service.getProductMaxStockByBranch("1"))
                .expectNextMatches(list ->
                        list.size() == 1 &&
                                list.getFirst().productName().equals("B") &&
                                list.getFirst().stock() == 50
                )
                .verifyComplete();
    }
}