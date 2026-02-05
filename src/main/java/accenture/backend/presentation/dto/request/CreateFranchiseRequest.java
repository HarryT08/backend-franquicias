package accenture.backend.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateFranchiseRequest (
        @NotBlank String name
) {}