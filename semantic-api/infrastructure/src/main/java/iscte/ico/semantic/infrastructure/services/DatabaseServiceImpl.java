package iscte.ico.semantic.infrastructure.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iscte.ico.semantic.application.interfaces.DatabaseService;
import iscte.ico.semantic.application.model.Query;
import iscte.ico.semantic.application.model.QueryParameters;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
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
        Query result = null;
        try {
            Instant start = Instant.now();
            _logger.info("Database Service - Executing query ...");
            iscte.ico.semantic.infrastructure.services.Query query = _queryDAO.getByID(queryID);
            result = new Query(query.getId(), query.getName(), new ObjectMapper().readValue(query.getQueryParameters(), QueryParameters[].class));
            Instant finish = Instant.now();
            _logger.info(new StringBuilder().append("Execution Time: ").append(Duration.between(start, finish).toMillis()).append(" ms").toString());

        } catch (JsonProcessingException e) {
            _logger.error(e.getMessage());
        } catch (Exception e) {
            _logger.error(e.getMessage());
        }

        return result;
    }

    @NotNull
    @Override
    public List<Query> getQueries() {
        List<Query> result = new ArrayList<>();
        try {
            Instant start = Instant.now();
            _logger.info("Database Service - Executing query ...");
            _queryDAO.getAll().forEach(x-> {
                try {
                    result.add(new Query(x.getId(), x.getName(), new ObjectMapper().readValue(x.getQueryParameters(), QueryParameters[].class)));
                } catch (JsonProcessingException e) {
                    _logger.error(e.getMessage());
                }
            });
            Instant finish = Instant.now();
            _logger.info(new StringBuilder().append(result.size()).append(" Rows").append(" - Execution Time: ").append(Duration.between(start, finish).toMillis()).append(" ms").toString());
        } catch (Exception e) {
            _logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public void createQuery(@NotNull String name, @NotNull List<QueryParameters> queryParameters) throws JsonProcessingException {
        try {
            _logger.info("Database Service - Executing query ...");
            iscte.ico.semantic.infrastructure.services.Query query = new iscte.ico.semantic.infrastructure.services.Query();
            query.setName(name);
            query.setQueryParameters(new ObjectMapper().writeValueAsString(queryParameters));
            _queryDAO.create(query);
            _logger.info("Database Service - Saved ...");
        } catch (Exception e) {
            _logger.error(e.getMessage());
        }
    }

    @Override
    public void updateQuery(@NotNull UUID id, @NotNull String name, @NotNull List<QueryParameters> queryParameters) throws JsonProcessingException {
        try {
            _logger.info("Database Service - Executing query ...");
            iscte.ico.semantic.infrastructure.services.Query query = new iscte.ico.semantic.infrastructure.services.Query();
            query.setId(id);
            query.setName(name);
            query.setQueryParameters(new ObjectMapper().writeValueAsString(queryParameters));
            _queryDAO.change(query);
            _logger.info("Database Service - Saved ...");
        } catch (Exception e) {
            _logger.error(e.getMessage());
        }
    }

    @Override
    public void deleteQuery(@NotNull UUID id){
        try {
            _logger.info("Database Service - Executing query ...");
            _queryDAO.remove(id);
            _logger.info("Database Service - Saved ...");
        } catch (Exception e) {
            _logger.error(e.getMessage());
        }
    }
}
