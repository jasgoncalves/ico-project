package iscte.ico.semantic.infrastructure.persistence;

import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class QueryDAOImpl extends AbstractDAO<Query> implements QueryDAO {

    private Transaction _transaction;

    public QueryDAOImpl(){
        super();
        setClazz(Query.class);
    }

    @Override
    public Query getByID(UUID id) {
        return findOne(id);
    }

    @Override
    public List<Query> getAll() {
        return findAll();
    }

    @Override
    public UUID create(Query query) {
        _transaction = getCurrentSession().beginTransaction();
        save(query);
        getCurrentSession().flush();
        _transaction.commit();
        return query.getId();
    }

    @Override
    public Query change(Query query) {
        _transaction = getCurrentSession().beginTransaction();
        Query result = update(query);
        _transaction.commit();
        return result;
    }

    @Override
    public void remove(UUID id) {
        _transaction = getCurrentSession().beginTransaction();
        deleteById(id);
        _transaction.commit();

    }
}
