package accenture.backend.application.dto;

public record ProductWithBranch(
        String branchName,
        String productName,
        Long stock
) {}