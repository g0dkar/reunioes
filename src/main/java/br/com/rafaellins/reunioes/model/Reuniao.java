package br.com.rafaellins.reunioes.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Reuniao {
    @NotNull
    UUID id;

    @NotNull
    @Size(max = 128)
    String titulo;

    @NotNull
    @Size(max = 512)
    String descricao;

    @NotNull
    @FutureOrPresent
    OffsetDateTime dataHora;

    @NotNull
    @NotEmpty
    List<Participante> participantes;

    @NotNull
    @NotEmpty
    List<Direcionamento> direcionamentos;
}
