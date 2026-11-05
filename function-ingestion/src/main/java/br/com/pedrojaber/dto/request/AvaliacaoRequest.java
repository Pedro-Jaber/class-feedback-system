package br.com.pedrojaber.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoRequest(
        @NotBlank(message = "descricao é obrigatória") String descricao,
        @NotNull(message = "nota é obrigatória") @Min(value = 0, message = "nota deve estar entre 0 e 10") @Max(value = 10, message = "nota deve estar entre 0 e 10") Integer nota) {

}
