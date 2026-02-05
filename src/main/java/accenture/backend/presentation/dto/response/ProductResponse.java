package accenture.backend.presentation.dto.response;

public record ProductResponse(
        String id,
        String name,
        Long stock
) {
}
