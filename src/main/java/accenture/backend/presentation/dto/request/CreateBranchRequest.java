package accenture.backend.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateBranchRequest(
        @NotBlank String name
) { }