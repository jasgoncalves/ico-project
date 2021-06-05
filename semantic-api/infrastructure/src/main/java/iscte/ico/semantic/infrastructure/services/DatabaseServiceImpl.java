package iscte.ico.semantic.infrastructure.services;

import iscte.ico.semantic.application.interfaces.DatabaseService;
import iscte.ico.semantic.application.model.Query;
import iscte.ico.semantic.application.model.QueryParameters;
import iscte.ico.semantic.infrastructure.persistence.interfaces.QueryDAO;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private QueryDAO _queryDAO;
    private Logger _logger;

    @Autowired
    public DatabaseServiceImpl (QueryDAO queryDAO, Logger logger) {
        _logger = logger;
        _queryDAO = queryDAO;
    }

    @NotNull
    @Override
    public Query getQuery(@NotNull UUID queryID) {
        try {
            iscte.ico.semantic.infrastructure.model.Query query = _queryDAO.getByID(queryID);
            Query result = new Query(query.getId(), query.getName(), query.getQueryParameters());
            return result;

        } catch (Exception e) {
            _logger.error(e.getMessage());
            throw e;
        }
    }

    @NotNull
    @Override
    public List<Query> getQueries() {
        List<Query> result = new ArrayList<>();
        _queryDAO.getAll().forEach(x-> result.add(new Query(x.getId(), x.getName(), x.getQueryParameters())));
        return result;
    }

    @Override
    public void createQuery(@NotNull String name, @NotNull List<QueryParameters> queryParameters) {
        iscte.ico.semantic.infrastructure.model.Query query = new iscte.ico.semantic.infrastructure.model.Query();
        query.setName(name);
        query.setQueryParameters(queryParameters);
        _queryDAO.create(query);
    }
}
