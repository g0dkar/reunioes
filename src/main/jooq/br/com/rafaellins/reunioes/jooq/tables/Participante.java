/*
 * This file is generated by jOOQ.
 */
package br.com.rafaellins.reunioes.jooq.tables;


import br.com.rafaellins.reunioes.jooq.Keys;
import br.com.rafaellins.reunioes.jooq.Public;
import br.com.rafaellins.reunioes.jooq.tables.records.ParticipanteRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Participante extends TableImpl<ParticipanteRecord> {

    private static final long serialVersionUID = 1468725236;

    /**
     * The reference instance of <code>public.participante</code>
     */
    public static final Participante PARTICIPANTE = new Participante();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ParticipanteRecord> getRecordType() {
        return ParticipanteRecord.class;
    }

    /**
     * The column <code>public.participante.id</code>.
     */
    public final TableField<ParticipanteRecord, UUID> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.participante.nome</code>.
     */
    public final TableField<ParticipanteRecord, String> NOME = createField(DSL.name("nome"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>public.participante.hash_nome</code>.
     */
    public final TableField<ParticipanteRecord, String> HASH_NOME = createField(DSL.name("hash_nome"), org.jooq.impl.SQLDataType.VARCHAR(64), this, "");

    /**
     * Create a <code>public.participante</code> table reference
     */
    public Participante() {
        this(DSL.name("participante"), null);
    }

    /**
     * Create an aliased <code>public.participante</code> table reference
     */
    public Participante(String alias) {
        this(DSL.name(alias), PARTICIPANTE);
    }

    /**
     * Create an aliased <code>public.participante</code> table reference
     */
    public Participante(Name alias) {
        this(alias, PARTICIPANTE);
    }

    private Participante(Name alias, Table<ParticipanteRecord> aliased) {
        this(alias, aliased, null);
    }

    private Participante(Name alias, Table<ParticipanteRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    public <O extends Record> Participante(Table<O> child, ForeignKey<O, ParticipanteRecord> key) {
        super(child, key, PARTICIPANTE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<ParticipanteRecord> getPrimaryKey() {
        return Keys.PARTICIPANTE_PKEY;
    }

    @Override
    public List<UniqueKey<ParticipanteRecord>> getKeys() {
        return Arrays.<UniqueKey<ParticipanteRecord>>asList(Keys.PARTICIPANTE_PKEY);
    }

    @Override
    public Participante as(String alias) {
        return new Participante(DSL.name(alias), this);
    }

    @Override
    public Participante as(Name alias) {
        return new Participante(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Participante rename(String name) {
        return new Participante(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Participante rename(Name name) {
        return new Participante(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<UUID, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
