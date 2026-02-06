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

import java.util.List;

@RestController
@RequestMapping("/api/franchises")
@AllArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FranchiseResponse createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        return FranchiseMapper.toFranchiseResponse(franchiseService.createFranchise(request.name()));
    }

    @PutMapping("/{idFranchise}/name")
    public FranchiseResponse updateFranchiseName(@PathVariable String idFranchise, @Valid @RequestBody UpdateNameRequest request) {
        return FranchiseMapper.toFranchiseResponse(franchiseService.updateNameFranchise(idFranchise, request.name()));
    }

    @PostMapping("/{idFranchise}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public FranchiseResponse addBranchToFranchise(@PathVariable String idFranchise, @Valid @RequestBody CreateFranchiseRequest request) {
        return FranchiseMapper.toFranchiseResponse(franchiseService.addBranchToFranchise(idFranchise, request.name()));
    }

    @PutMapping("/{idFranchise}/branches/{idBranch}/name")
    public FranchiseResponse updateBranchName(@PathVariable String idFranchise, @PathVariable String idBranch, @Valid @RequestBody UpdateNameRequest request) {
        return FranchiseMapper.toFranchiseResponse(franchiseService.updateNameBranch(idFranchise, idBranch, request.name()));
    }

    @PostMapping("/{idFranchise}/branches/{idBranch}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public FranchiseResponse addProductToBranch(@PathVariable String idFranchise, @PathVariable String idBranch, @Valid @RequestBody CreateProduct request) {
        return FranchiseMapper.toFranchiseResponse(franchiseService.addProductToBranch(idFranchise, idBranch, request.name(), request.stock()));
    }

    @DeleteMapping("/{idFranchise}/branches/{idBranch}/products/{idProduct}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProductFromBranch(@PathVariable String idFranchise, @PathVariable String idBranch, @PathVariable String idProduct) {
        franchiseService.deleteProduct(idFranchise, idBranch, idProduct);
    }

    @PutMapping("/{idFranchise}/branches/{idBranch}/products/{idProduct}/stock")
    public FranchiseResponse updateProductStock(@PathVariable String idFranchise, @PathVariable String idBranch, @PathVariable String idProduct, @Valid @RequestBody UpdateStockRequest request) {
        return FranchiseMapper.toFranchiseResponse(franchiseService.updateStockProduct(idFranchise, idBranch, idProduct, request.stock()));
    }

    @GetMapping("/{idFranchise}/products-max-stock")
    public List<ProductMaxStockResponse> getProductsWithMaxStock(@PathVariable String idFranchise) {
        return franchiseService.getProductMaxStockByBranch(idFranchise)
                .stream()
                .map(p -> new ProductMaxStockResponse(
                        p.branchName(),
                        p.productName(),
                        p.stock()
                ))
                .toList();
    }
}