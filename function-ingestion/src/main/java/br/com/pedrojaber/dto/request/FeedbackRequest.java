package br.com.pedrojaber.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequest(
        @JsonProperty("descricao") @NotBlank(message = "description is required") String description,

        @JsonProperty("nota") @NotNull(message = "grade is required") @Min(value = 0, message = "grade must be between 0 and 10") @Max(value = 10, message = "grade must be between 0 and 10") Integer grade) {

}
