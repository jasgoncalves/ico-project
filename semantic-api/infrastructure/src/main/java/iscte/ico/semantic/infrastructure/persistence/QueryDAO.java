package iscte.ico.semantic.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

public interface QueryDAO {

    Query getByID(UUID id);
    List<Query> getAll();
    UUID create(Query query);
    Query change(Query query);
    void remove(UUID id);
}
