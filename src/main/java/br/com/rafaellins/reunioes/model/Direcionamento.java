package br.com.rafaellins.reunioes.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class Direcionamento {
    @NotNull
    UUID id;

    @NotNull
    UUID participante;

    @NotNull
    @Size(max = 256)
    String direcionamento;

    @NotNull
    OffsetDateTime prazo;
}
