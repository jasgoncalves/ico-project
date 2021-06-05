package iscte.ico.semantic.infrastructure.persistence;

import iscte.ico.semantic.infrastructure.model.Query;
import iscte.ico.semantic.infrastructure.persistence.interfaces.QueryDAO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class QueryDAOImpl extends AbstractDAO<Query> implements QueryDAO {

    public QueryDAOImpl(){
        setClazz(Query.class);
    }

    @Override
    public Query getByID(UUID id) {
        return this.findOne(id);
    }

    @Override
    public List<Query> getAll() {
        return this.findAll();
    }

    @Override
    public void create(Query query) {
        this.save(query);
    }

    @Override
    public Query change(Query query) {
        return this.update(query);
    }

    @Override
    public void remove(UUID id) {
        this.deleteById(id);
    }
}
