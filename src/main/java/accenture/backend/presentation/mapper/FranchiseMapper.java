package accenture.backend.presentation.mapper;

import accenture.backend.domain.model.Branch;
import accenture.backend.domain.model.Franchise;
import accenture.backend.domain.model.Product;
import accenture.backend.presentation.dto.response.BranchResponse;
import accenture.backend.presentation.dto.response.FranchiseResponse;
import accenture.backend.presentation.dto.response.ProductResponse;

public class FranchiseMapper {

    public static FranchiseResponse toFranchiseResponse(Franchise franchise) {
        return new FranchiseResponse(
                franchise.getId(),
                franchise.getName(),
                franchise.getBranches().stream()
                        .map(FranchiseMapper::toBranchResponse)
                        .toList()
        );
    }

    public static BranchResponse toBranchResponse(Branch branch) {
        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getProducts().stream()
                        .map(FranchiseMapper::toProductResponse)
                        .toList()
        );
    }

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getStock()
        );
    }
}
