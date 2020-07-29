/*
 * This file is generated by jOOQ.
 */
package br.com.rafaellins.reunioes.jooq.tables.records;


import br.com.rafaellins.reunioes.jooq.tables.Participante;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ParticipanteRecord extends UpdatableRecordImpl<ParticipanteRecord> implements Record3<UUID, String, String> {

    private static final long serialVersionUID = -688622882;

    /**
     * Setter for <code>public.participante.id</code>.
     */
    public ParticipanteRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.participante.id</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.participante.nome</code>.
     */
    public ParticipanteRecord setNome(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.participante.nome</code>.
     */
    public String getNome() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.participante.hash_nome</code>.
     */
    public ParticipanteRecord setHashNome(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.participante.hash_nome</code>.
     */
    public String getHashNome() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<UUID, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return Participante.PARTICIPANTE.ID;
    }

    @Override
    public Field<String> field2() {
        return Participante.PARTICIPANTE.NOME;
    }

    @Override
    public Field<String> field3() {
        return Participante.PARTICIPANTE.HASH_NOME;
    }

    @Override
    public UUID component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getNome();
    }

    @Override
    public String component3() {
        return getHashNome();
    }

    @Override
    public UUID value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getNome();
    }

    @Override
    public String value3() {
        return getHashNome();
    }

    @Override
    public ParticipanteRecord value1(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public ParticipanteRecord value2(String value) {
        setNome(value);
        return this;
    }

    @Override
    public ParticipanteRecord value3(String value) {
        setHashNome(value);
        return this;
    }

    @Override
    public ParticipanteRecord values(UUID value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ParticipanteRecord
     */
    public ParticipanteRecord() {
        super(Participante.PARTICIPANTE);
    }

    /**
     * Create a detached, initialised ParticipanteRecord
     */
    public ParticipanteRecord(UUID id, String nome, String hashNome) {
        super(Participante.PARTICIPANTE);

        set(0, id);
        set(1, nome);
        set(2, hashNome);
    }
}