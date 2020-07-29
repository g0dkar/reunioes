/*
 * This file is generated by jOOQ.
 */
package br.com.rafaellins.reunioes.jooq.tables.records;


import br.com.rafaellins.reunioes.jooq.tables.ReuniaoParticipante;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;

import java.util.UUID;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ReuniaoParticipanteRecord extends TableRecordImpl<ReuniaoParticipanteRecord> implements Record2<UUID, UUID> {

    private static final long serialVersionUID = 1187285593;

    /**
     * Setter for <code>public.reuniao_participante.reuniao_id</code>.
     */
    public ReuniaoParticipanteRecord setReuniaoId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.reuniao_participante.reuniao_id</code>.
     */
    public UUID getReuniaoId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.reuniao_participante.participante_id</code>.
     */
    public ReuniaoParticipanteRecord setParticipanteId(UUID value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.reuniao_participante.participante_id</code>.
     */
    public UUID getParticipanteId() {
        return (UUID) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, UUID> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<UUID, UUID> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return ReuniaoParticipante.REUNIAO_PARTICIPANTE.REUNIAO_ID;
    }

    @Override
    public Field<UUID> field2() {
        return ReuniaoParticipante.REUNIAO_PARTICIPANTE.PARTICIPANTE_ID;
    }

    @Override
    public UUID component1() {
        return getReuniaoId();
    }

    @Override
    public UUID component2() {
        return getParticipanteId();
    }

    @Override
    public UUID value1() {
        return getReuniaoId();
    }

    @Override
    public UUID value2() {
        return getParticipanteId();
    }

    @Override
    public ReuniaoParticipanteRecord value1(UUID value) {
        setReuniaoId(value);
        return this;
    }

    @Override
    public ReuniaoParticipanteRecord value2(UUID value) {
        setParticipanteId(value);
        return this;
    }

    @Override
    public ReuniaoParticipanteRecord values(UUID value1, UUID value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ReuniaoParticipanteRecord
     */
    public ReuniaoParticipanteRecord() {
        super(ReuniaoParticipante.REUNIAO_PARTICIPANTE);
    }

    /**
     * Create a detached, initialised ReuniaoParticipanteRecord
     */
    public ReuniaoParticipanteRecord(UUID reuniaoId, UUID participanteId) {
        super(ReuniaoParticipante.REUNIAO_PARTICIPANTE);

        set(0, reuniaoId);
        set(1, participanteId);
    }
}