package accenture.backend.presentation.dto.response;

import java.util.List;

public record FranchiseResponse(
        String id,
        String name,
        List<BranchResponse> branches
) {
}
