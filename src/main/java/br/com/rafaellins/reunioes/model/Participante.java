package br.com.rafaellins.reunioes.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
public class Participante {
    @NotNull
    UUID id;

    @NotNull
    @Size(max = 128)
    String nome;

    @NotNull
    String hashNome;
}
