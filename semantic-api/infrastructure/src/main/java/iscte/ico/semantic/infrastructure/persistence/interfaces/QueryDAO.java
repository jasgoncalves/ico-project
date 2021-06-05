package iscte.ico.semantic.infrastructure.persistence.interfaces;

import iscte.ico.semantic.infrastructure.model.Query;

import java.util.List;
import java.util.UUID;

public interface QueryDAO {

    Query getByID(UUID id);
    List<Query> getAll();
    void create(Query query);
    Query change(Query query);
    void remove(UUID id);
}
