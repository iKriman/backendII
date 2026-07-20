package cl.duoc.minimarket.orders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderRequest(
        @NotBlank String customerName,
        @NotBlank String deliveryType,
        @NotEmpty List<@Valid OrderItemRequest> items) {
}
