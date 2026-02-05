package accenture.backend.presentation.dto.response;

public record ProductMaxStockResponse(
        String nameBranch,
        String nameProduct,
        Long stock
) {
}