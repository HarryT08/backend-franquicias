package accenture.backend.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProduct(
        @NotBlank String name,
        @NotNull @Min(0) Long stock
) {
}
