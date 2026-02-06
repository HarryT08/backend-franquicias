package accenture.backend.presentation.controllers;

import accenture.backend.application.services.FranchiseService;
import accenture.backend.presentation.dto.request.CreateFranchiseRequest;
import accenture.backend.presentation.dto.request.CreateProduct;
import accenture.backend.presentation.dto.request.UpdateNameRequest;
import accenture.backend.presentation.dto.request.UpdateStockRequest;
import accenture.backend.presentation.dto.response.FranchiseResponse;
import accenture.backend.presentation.dto.response.ProductMaxStockResponse;
import accenture.backend.presentation.mapper.FranchiseMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/franchises")
@AllArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        return franchiseService.createFranchise(request.name()).map(FranchiseMapper::toFranchiseResponse);
    }

    @PutMapping("/{idFranchise}/name")
    public Mono<FranchiseResponse> updateFranchiseName(@PathVariable String idFranchise, @Valid @RequestBody UpdateNameRequest request) {
        return franchiseService.updateNameFranchise(idFranchise, request.name()).map(FranchiseMapper::toFranchiseResponse);
    }

    @PostMapping("/{idFranchise}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> addBranchToFranchise(@PathVariable String idFranchise, @Valid @RequestBody CreateFranchiseRequest request) {
        return franchiseService.addBranchToFranchise(idFranchise, request.name()).map(FranchiseMapper::toFranchiseResponse);
    }

    @PutMapping("/{idFranchise}/branches/{idBranch}/name")
    public Mono<FranchiseResponse> updateBranchName(@PathVariable String idFranchise, @PathVariable String idBranch, @Valid @RequestBody UpdateNameRequest request) {
        return franchiseService.updateNameBranch(idFranchise, idBranch, request.name()).map(FranchiseMapper::toFranchiseResponse);
    }

    @PostMapping("/{idFranchise}/branches/{idBranch}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> addProductToBranch(@PathVariable String idFranchise, @PathVariable String idBranch, @Valid @RequestBody CreateProduct request) {
        return franchiseService.addProductToBranch(idFranchise, idBranch, request.name(), request.stock()).map(FranchiseMapper::toFranchiseResponse);
    }

    @DeleteMapping("/{idFranchise}/branches/{idBranch}/products/{idProduct}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> removeProductFromBranch(@PathVariable String idFranchise, @PathVariable String idBranch, @PathVariable String idProduct) {
        return franchiseService.deleteProduct(idFranchise, idBranch, idProduct);
    }

    @PutMapping("/{idFranchise}/branches/{idBranch}/products/{idProduct}/stock")
    public Mono<FranchiseResponse> updateProductStock(@PathVariable String idFranchise, @PathVariable String idBranch, @PathVariable String idProduct, @Valid @RequestBody UpdateStockRequest request) {
        return franchiseService.updateStockProduct(idFranchise, idBranch, idProduct, request.stock()).map(FranchiseMapper::toFranchiseResponse);
    }

    @GetMapping("/{idFranchise}/products-max-stock")
    public Flux<ProductMaxStockResponse> getProductsWithMaxStock(@PathVariable String idFranchise) {
        return franchiseService.getProductMaxStockByBranch(idFranchise)
                .flatMapMany(Flux::fromIterable)
                .map(p ->
                    new ProductMaxStockResponse(
                            p.branchName(),
                            p.productName(),
                            p.stock()
                    )
                );
    }
}