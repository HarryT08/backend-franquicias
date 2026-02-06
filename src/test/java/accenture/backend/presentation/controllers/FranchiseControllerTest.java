package accenture.backend.presentation.controllers;

import accenture.backend.application.services.FranchiseService;
import accenture.backend.domain.model.Franchise;
import accenture.backend.presentation.dto.request.CreateFranchiseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

class FranchiseControllerTest {

    private WebTestClient webTestClient;
    private FranchiseService franchiseService;

    @BeforeEach
    void setUp() {
        franchiseService = mock(FranchiseService.class);

        FranchiseController controller =
                new FranchiseController(franchiseService);

        webTestClient = WebTestClient
                .bindToController(controller)
                .build();
    }

    @Test
    void shouldCreateFranchise() {
        Franchise franchise = Franchise.builder()
                .id("1")
                .name("Test Franchise")
                .branches(new ArrayList<>())
                .build();

        Mockito.when(franchiseService.createFranchise(anyString()))
                .thenReturn(Mono.just(franchise));

        webTestClient.post()
                .uri("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CreateFranchiseRequest("Test Franchise"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Test Franchise");
    }
}
