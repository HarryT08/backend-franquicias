package accenture.backend.application.services;

import accenture.backend.application.dto.ProductWithBranch;
import accenture.backend.domain.exceptions.ResourceNotFoundException;
import accenture.backend.domain.model.Branch;
import accenture.backend.domain.model.Franchise;
import accenture.backend.domain.model.Product;
import accenture.backend.domain.ports.FranchiseRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FranchiseService {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<Franchise> createFranchise(String name) {
        Franchise franchise = Franchise.builder().name(name).build();

        return franchiseRepositoryPort.save(franchise);
    }

    public Mono<Franchise> updateNameFranchise(String idFranchise, String newName) {
        return this.getFranchiseMono(idFranchise)
                .map(f -> {
                    f.setName(newName);
                    return f;
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    public Mono<Franchise> addBranchToFranchise(String idFranchise, String nameBranch) {
        return this.getFranchiseMono(idFranchise)
                .map(f -> {
                    f.getBranches().add(
                            Branch.builder()
                                    .id(UUID.randomUUID().toString())
                                    .name(nameBranch).build()
                    );
                    return f;
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    public Mono<Franchise> updateNameBranch(String idFranchise, String idBranch, String newName) {
        return this.getFranchiseMono(idFranchise)
                .map(f -> {
                    Branch branch = getBranch(f, idBranch);
                    branch.setName(newName);
                    return f;
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    public Mono<Franchise> addProductToBranch(String idFranchise, String idBranch, String nameProduct, Long stock) {
        return this.getFranchiseMono(idFranchise)
                .map(f -> {
                    Branch branch = getBranch(f, idBranch);
                    branch.getProducts().add(
                            Product.builder()
                                    .id(UUID.randomUUID().toString())
                                    .name(nameProduct)
                                    .stock(stock).build()
                    );
                    return f;
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    public Mono<Void> deleteProduct(String idFranchise, String idBranch, String idProduct) {
        return this.getFranchiseMono(idFranchise)
                .map(f -> {
                    Branch branch = getBranch(f, idBranch);
                    Product product = getProduct(f, idBranch, idProduct);
                    branch.getProducts().remove(product);
                    return f;
                })
                .flatMap(franchiseRepositoryPort::save)
                .then();
    }

    public Mono<Franchise> updateStockProduct(String idFranchise, String idBranch, String idProduct, Long newStock) {
        return this.getFranchiseMono(idFranchise)
                .map(f -> {
                    Product product = getProduct(f, idBranch, idProduct);
                    product.setStock(newStock);
                    return f;
                })
                .flatMap(franchiseRepositoryPort::save);
    }

    public Mono<List<ProductWithBranch>> getProductMaxStockByBranch(String idFranchise){
        return this.getFranchiseMono(idFranchise)
                .map(f -> f.getBranches().stream()
                        .filter(b -> Objects.nonNull(b.getProducts()) && !b.getProducts().isEmpty())
                        .map(b -> {
                            Product maxStockProduct = b.getProducts().stream()
                                    .max(Comparator.comparingLong(Product::getStock))
                                    .orElseThrow(() -> new ResourceNotFoundException("No products found in branch"));
                            return new ProductWithBranch(b.getName(), maxStockProduct.getName(), maxStockProduct.getStock());
                        })
                        .toList()
                );
    }

    private Mono<Franchise> getFranchiseMono(String id) {
        return franchiseRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Franchise not found")));
    }

    private Branch getBranch(Franchise franchise, String idSucursal) {
        return franchise.getBranches().stream()
                .filter(s -> s.getId().equals(idSucursal))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }

    private Product getProduct(Franchise franchise, String idBranch, String idProduct) {
        Branch branch = getBranch(franchise, idBranch);

        return branch.getProducts().stream()
                .filter(p -> p.getId().equals(idProduct))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}
