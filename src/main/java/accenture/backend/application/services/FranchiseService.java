package accenture.backend.application.services;

import accenture.backend.application.dto.ProductWithBranch;
import accenture.backend.domain.exceptions.ResourceNotFoundException;
import accenture.backend.domain.model.Branch;
import accenture.backend.domain.model.Franchise;
import accenture.backend.domain.model.Product;
import accenture.backend.domain.ports.FranchiseRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FranchiseService {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Franchise createFranchise(String name) {
        Franchise franchise = Franchise.builder().name(name).build();

        return franchiseRepositoryPort.save(franchise);
    }

    public Franchise updateNameFranchise(String idFranchise, String newName) {
        Franchise franchise = getFranchise(idFranchise);
        franchise.setName(newName);
        return franchiseRepositoryPort.save(franchise);
    }

    public Franchise addBranchToFranchise(String idFranchise, String nameBranch) {
        Franchise franchise = getFranchise(idFranchise);
        franchise.getBranches().add(
                Branch.builder()
                        .id(UUID.randomUUID().toString())
                        .name(nameBranch).build()
        );
        return franchiseRepositoryPort.save(franchise);
    }

    public Franchise updateNameBranch(String idFranchise, String idSucursal, String newName) {
        Franchise franchise = getFranchise(idFranchise);
        Branch branch = getBranch(franchise, idSucursal);
        branch.setName(newName);
        return franchiseRepositoryPort.save(franchise);
    }

    public Franchise addProductToBranch(String idFranchise, String idBranch, String nameProduct, Long stock) {
        Franchise franchise = getFranchise(idFranchise);
        Branch branch = getBranch(franchise, idBranch);
        branch.getProducts().add(
                Product.builder()
                        .id(UUID.randomUUID().toString())
                        .name(nameProduct)
                        .stock(stock).build()
        );
        return franchiseRepositoryPort.save(franchise);
    }

    public Franchise deleteProduct(String idFranchise, String idBranch, String idProduct) {
        Franchise franchise = getFranchise(idFranchise);
        Branch branch = getBranch(franchise, idBranch);
        Product product = getProduct(franchise, idBranch, idProduct);
        branch.getProducts().remove(product);
        return franchiseRepositoryPort.save(franchise);
    }

    public Franchise updateStockProduct(String idFranchise, String idBranch, String idProduct, Long newStock) {
        Franchise franchise = getFranchise(idFranchise);
        Product product = getProduct(franchise, idBranch, idProduct);
        product.setStock(newStock);
        return franchiseRepositoryPort.save(franchise);
    }

    public List<ProductWithBranch> getProductMaxStockByBranch(String idFranchise){
        Franchise franchise = getFranchise(idFranchise);

        return franchise.getBranches().stream()
                .map(branch -> branch.getProducts().stream()
                        .max(Comparator.comparing(Product::getStock))
                        .map(p -> new ProductWithBranch(
                                branch.getName(),
                                p.getName(),
                                p.getStock()
                        ))
                        .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    private Franchise getFranchise(String idFranchise) {
        return franchiseRepositoryPort.findById(idFranchise)
                .orElseThrow(() -> new ResourceNotFoundException("Franchise not found"));
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
