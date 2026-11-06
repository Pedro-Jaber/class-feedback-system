package br.com.pedrojaber.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequest(
                @NotBlank(message = "descricao is required") String description,

                @NotNull(message = "nota is required") @Min(value = 0, message = "nota must be between 0 and 10") @Max(value = 10, message = "nota must be between 0 and 10") Integer grade) {

}
