package iscte.ico.semantic.infrastructure.services;

import iscte.ico.semantic.application.interfaces.OntologyService;
import iscte.ico.semantic.application.interfaces.OwlService;
import iscte.ico.semantic.application.interfaces.SQWRLService;
import iscte.ico.semantic.application.model.QueryParameters;
import iscte.ico.semantic.application.model.QueryResult;
import iscte.ico.semantic.application.model.SchedulingProblemRequest;
import iscte.ico.semantic.domain.entities.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class OwlServiceImpl implements OwlService {

//    @Autowired
    private SQWRLService _sqwrlService;
//    @Autowired
    private OntologyService _ontologyService;

    @Autowired
    public OwlServiceImpl (SQWRLService sqwrlService, OntologyService ontologyService){
        _sqwrlService = sqwrlService;
        _ontologyService = ontologyService;
    }

    @NotNull
    @Override
    public QueryResult executeQuery(@NotNull List<QueryParameters> queryParameters) throws Exception {
        return _sqwrlService.query(queryParameters);
    }

    @NotNull
    @Override
    public QueryResult executeQuery(@NotNull String queryName) throws Exception {
        return _sqwrlService.query(queryName);
    }

    @NotNull
    @Override
    public List<SwrlRelationalOperator> getRelationalOperator() throws IOException {
        return _sqwrlService.getRelationalOperator();
    }

    @NotNull
    @Override
    public List<OwlClass> getClasses() {
        return _ontologyService.getClasses();
    }

    @NotNull
    @Override
    public List<OwlIndividual> getIndividuals() {
        return _ontologyService.getIndividuals();
    }

    @NotNull
    @Override
    public List<OwlDatatypeProperty> getDatatypeProperties() {
        return _ontologyService.getDatatypeProperties();
    }

    @NotNull
    @Override
    public List<OwlObjectProperty> getObjectProperties() {
        return _ontologyService.getObjectProperties();
    }

    @Override
    public byte[] addSchedulingProblemIndividual(@Nullable SchedulingProblemRequest schedulingProblemRequest) throws IOException {
        return _ontologyService.addSchedulingProblemIndividual(schedulingProblemRequest);
    }
}
