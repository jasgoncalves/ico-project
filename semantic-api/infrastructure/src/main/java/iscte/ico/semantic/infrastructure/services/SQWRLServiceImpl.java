package iscte.ico.semantic.infrastructure.services;

import iscte.ico.semantic.application.interfaces.OntologyService;
import iscte.ico.semantic.application.interfaces.SQWRLService;
import iscte.ico.semantic.application.model.QueryEntityType;
import iscte.ico.semantic.application.model.QueryParameters;
import iscte.ico.semantic.application.model.QueryResult;
import iscte.ico.semantic.domain.entities.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SQWRLServiceImpl implements SQWRLService {

    private OntologyService _ontologyService;
    private SQWRLQueryEngine _queryEngine;
    private Scanner _builtinSwrl;
    private List<OwlClass> _classes;
    private List<OwlIndividual> _individuals;
    private List<OwlDatatypeProperty> _datatypePropeties;
    private List<OwlObjectProperty> _objectProperties;
    private Logger _logger;

    public SQWRLServiceImpl(Logger logger, OntologyService ontologyService){
        _logger = logger;
        _ontologyService = ontologyService;
    }

    public SQWRLServiceImpl run(){
        try {
            _logger.info("SQWRL Sevice - Starting...");
            startQueryEngine();
            startOntologyService();
            _logger.info("SQWRL Sevice - Started...");

        } catch (Exception exception){
            _logger.error(exception.getMessage());
            _logger.info("SQWRL Service NOT started.");
            throw exception;
        }
        return this;
    }

    private void startOntologyService(){
        _logger.info("SQWRL Sevice - Loading Ontology Data...");

        _classes = _ontologyService.getClasses();
        _individuals = _ontologyService.getIndividuals();
        _datatypePropeties = _ontologyService.getDatatypeProperties();
        _objectProperties = _ontologyService.getObjectProperties();

        _logger.info("SQWRL Sevice - Ontology Data Loades...");

    }

    private void startQueryEngine(){

        try{
            _logger.info("SQWRL Sevice - Query Engine Starting...");
            String filePath = new File("C:\\Users\\jorge\\OneDrive\\Ambiente de Trabalho\\ISCTE\\MEI_1stYear_2stSemester\\ICO\\ico-project\\semantic-api\\infrastructure\\target\\classes\\PMOEA.owl").getAbsolutePath();

            Optional<String> owlFilename = Optional.of(filePath);
            Optional<File> owlFile = Optional.of(new File(owlFilename.get()));

            OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(owlFile.get());
            _queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
            _logger.info("SQWRL Sevice - Query Engine Started.");

        } catch (OWLOntologyCreationException e) {
            _logger.error(e.getMessage());
            _logger.info("SQWRL Query Engine NOT started.");
        }

    }

    @Override
    public List<SwrlRelationalOperator> getRelationalOperator() throws FileNotFoundException {

        _logger.info("SQWRL Sevice - Getting relation operators list");
        List<SwrlRelationalOperator> listRelationalOperators = new ArrayList<>();
        try {

            _builtinSwrl = new Scanner(new File("C:\\Users\\jorge\\OneDrive\\Ambiente de Trabalho\\ISCTE\\MEI_1stYear_2stSemester\\ICO\\ico-project\\semantic-api\\infrastructure\\target\\classes\\builtinswrl.csv"));
            _builtinSwrl.useDelimiter(";");

            while (_builtinSwrl.hasNext()) {
                listRelationalOperators.add(new SwrlRelationalOperator(_builtinSwrl.next().replace("\r\n", ""), _builtinSwrl.next(), _builtinSwrl.next()));
            }
            _builtinSwrl.close();

            _logger.info(new StringBuilder().append("SQWRL Sevice - Return rows:").append(listRelationalOperators.size()).toString());
            return listRelationalOperators;

        }catch(Exception e)
        {
            _logger.error(new StringBuilder().append("SQWRL Sevice - ").append(e.getMessage()).toString());
            throw e;
        }
        finally {
            _logger.info("SQWRL Sevice - End getting relation operators list");
        }
    }

    @NotNull
    @Override
    public QueryResult query(List<QueryParameters> queryParameters) throws Exception {

        String queryName = UUID.randomUUID().toString();
        List<Dictionary<String, String>> results = new ArrayList<>();
        String query = buildQuery(queryParameters);
        _logger.info(new StringBuilder().append("SQWRL Sevice - Starting query:").append(queryName).append(" - ").append(query).toString());

        try{

            _logger.info(new StringBuilder().append("SQWRL Sevice - Executing query ...").toString());
            final SQWRLResult result = _queryEngine.runSQWRLQuery(queryName, query);

            while (result.next()){
                Dictionary<String, String> dictionary = new Hashtable<>();
                for (int i = 0; i < result.getNumberOfColumns(); i++) {
                    if (result.hasNamedIndividualValue(i)) {
                        String label = getIndividualLabelById(result.getValue(result.getColumnName(i)).toString().replace("PMOEA:", ""));
                        if (label != null) {
                            dictionary.put(result.getColumnName(i),label);
                        }
                    }
                    else if (result.hasLiteralValue(i)) {
                        String label = result.getValue(result.getColumnName(i)).toString();
                        if (label != null) {
                            dictionary.put(result.getColumnName(i),label);
                        }
                    }
                }
                results.add(dictionary);
            }
            _logger.info(new StringBuilder().append("Returned Rows...").append(results.size()).toString());
            return new QueryResult(results, results.size(), query);
        } catch (SWRLParseException | SQWRLException swrlException) {
            _logger.error(new StringBuilder().append("SQWRL Sevice - ").append(swrlException.getMessage()).toString());
            throw new Exception(swrlException.getMessage());
        } finally {
            _logger.info(new StringBuilder().append("SQWRL Sevice - Query Execution Ended").toString());
        }
    }

    private String buildQuery (List<QueryParameters> queryParameters){

        StringBuilder queryString = new StringBuilder();

        Iterator<QueryParameters> queryParametersIterator = queryParameters.iterator();
        Boolean goToNextAtom = true;

        while (queryParametersIterator.hasNext()){

            QueryParameters queryParameter = queryParametersIterator.next();

            switch (queryParameter.getEntityType()){
                case Class: {
                        queryString
                            .append("PMOEA:")
                            .append(queryParameter.getEntity())
                            .append("(?")
                            .append(queryParameter.getArgs().get(0))
                            .append(")");
                        goToNextAtom = true;
                    }
                    break;
                case DatatypeProperty:
                    {
                        queryString
                                .append("PMOEA:")
                                .append(queryParameter.getEntity())
                                .append("(");

                        if(queryParameter.getArgs().size() > 1){
                            queryString
                                    .append("?")
                                    .append(queryParameter.getArgs().get(0))
                                    .append(", ?")
                                    .append(queryParameter.getArgs().get(1))
                                    .append(")");
                            goToNextAtom = true;
                        }
                        else {
                            queryString.append("?")
                                    .append(queryParameter.getArgs().get(0))
                                    .append(", ");
                            goToNextAtom = false;
                        }
                    }
                    break;
                case ObjectProperty:{
                        queryString
                                .append("PMOEA:")
                                .append(queryParameter.getEntity())
                                .append("(");

                        if(queryParameter.getArgs().size() > 1){
                            queryString
                                .append("?")
                                .append(queryParameter.getArgs().get(0))
                                .append(", ?")
                                .append(queryParameter.getArgs().get(1))
                                .append(")");
                            goToNextAtom = true;
                        }
                        else {
                            queryString.append("?")
                                .append(queryParameter.getArgs().get(0))
                                .append(", ");
                            goToNextAtom = false;
                        }
                    }
                    break;
                case RelationalOperator: {
                        queryString
                            .append(queryParameter.getEntity())
                            .append("(?")
                            .append(queryParameter.getArgs().get(0))
                            .append(", ");
                        goToNextAtom = false;
                    }
                    break;
                case Literal: {
                        queryString
                            .append(getLiteralByType(queryParameter.getArgs().get(0), queryParameter.getEntity()))
                            .append(")");
                        goToNextAtom = true;
                    }
                    break;
                case Individual: {
                        queryString
                            .append("PMOEA:")
                            .append(queryParameter.getEntity())
                            .append(")");
                        goToNextAtom = true;
                    }
                    break;
            }

            if(queryParametersIterator.hasNext() && goToNextAtom)
                queryString.append(" ^ ");
        }

        queryString.append(" -> sqwrl:select(");

        queryParametersIterator =
                queryParameters
                    .stream()
                    .filter(x->
                        x.getEntityType().equals(QueryEntityType.Class)
                        || x.getEntityType().equals(QueryEntityType.ObjectProperty)
                        || x.getEntityType().equals(QueryEntityType.DatatypeProperty)
                    )
                    .iterator();

        Dictionary<String,String> columns = new Hashtable<>();

        while (queryParametersIterator.hasNext()) {
            QueryParameters parameter = queryParametersIterator.next();
            String key = parameter.getArgs().size() > 1
                    ? parameter.getArgs().get(parameter.getEntityType().equals(QueryEntityType.DatatypeProperty) ? 1 : 0)
                    : parameter.getArgs().get(0);
            columns.put(key, parameter.getName());
        }

        Enumeration<String> keys = columns.keys();

        while (keys.hasMoreElements()){
            queryString.append("?")
                .append(keys.nextElement())
                .append(keys.hasMoreElements()?", ":")");
        }

        if(queryParametersIterator.hasNext()) {

            queryString.append(" ^ sqwrl:orderBy(");

            while (queryParametersIterator.hasNext()) {
                QueryParameters parameter =  queryParametersIterator.next();
                String order = parameter.getArgs().size() > 1
                        ? parameter.getArgs().get(parameter.getEntityType().equals(QueryEntityType.DatatypeProperty) ? 1 : 0)
                        : parameter.getArgs().get(0);
                queryString.append("?")
                        .append(order)
                        .append(queryParametersIterator.hasNext() ? ", " : ")");
            }
        }

        Enumeration<String> elements = columns.elements();

        queryString.append(" ^ sqwrl:columnNames(");

        while (elements.hasMoreElements()){
            queryString.append("\"")
                    .append(elements.nextElement())
                    .append("\"")
                    .append(elements.hasMoreElements()?", ":")");
        }

        return queryString.toString();
    }

//    private String getLabelByEntityType(QueryParameters queryParameters){
//        switch (queryParameters.getEntityType()){
//            case Class:
//                return getClassesLabelById(queryParameters.getEntity());
//            case DatatypeProperty:
//                return getDatatypePropertyLabelById(queryParameters.getEntity());
//            case ObjectProperty:
//                return getObjectPropertyDomainLabelById(queryParameters.getEntity());
//            default:
//                return null;
//
//        }
//    }

    private String getIndividualLabelById(String individualId){
        Optional<OwlIndividual> individual = _individuals
            .stream()
            .filter(x -> x.getId().equals(individualId))
            .findFirst();
        return individual.isPresent() ? individual.get().getLabel() : null;
    }

    private String getClassesLabelById(String classeId){
        Optional<OwlClass> clazz = _classes
                .stream()
                .filter(x -> x.getId().equals(classeId))
                .findFirst();
        return clazz.isPresent() ? clazz.get().getLabel() : null;
    }

    private String getClassesLabelByUri(String uri){
        Optional<OwlClass> clazz = _classes
                .stream()
                .filter(x -> x.getUri().equals(uri))
                .findFirst();
        return clazz.isPresent() ? clazz.get().getLabel() : null;
    }
    private String getLiteralByType(String literal, String datatypePropertyId){
        Optional<OwlDatatypeProperty> datatypeProperty = _datatypePropeties
                .stream()
                .filter(x -> x.getId().equals(datatypePropertyId))
                .findFirst();

        String dataType = datatypeProperty.isPresent() ? datatypeProperty.get().getDataType() : null;

        if(dataType.equals("boolean") || dataType.equals("integer") )
            return literal;
        else
            return String.format("\"%s\"", literal);
    }


    private String getDatatypePropertyLabelById(String datatypePropertyId){
        Optional<OwlDatatypeProperty> datatypeProperty = _datatypePropeties
                .stream()
                .filter(x -> x.getId().equals(datatypePropertyId))
                .findFirst();
        return datatypeProperty.isPresent() ? datatypeProperty.get().getLabel() : null;
    }

//    private String getDatatypePropertyDomainLabelById(String datatypePropertyId){
//        Optional<OwlDatatypeProperty> datatypeProperty = _datatypePropeties
//                .stream()
//                .filter(x -> x.getId().equals(datatypePropertyId))
//                .findFirst();
//        String domain = datatypeProperty.isPresent() ? datatypeProperty.get().getDomain() : null;
//
//        return domain == null ? null : getClassesLabelByUri(domain);
//    }

    private String getObjectPropertyLabelId(String objectPropertyId){
        Optional<OwlObjectProperty> objectProperty = _objectProperties
                .stream()
                .filter(x -> x.getId().equals(objectPropertyId))
                .findFirst();
        return objectProperty.isPresent() ? objectProperty.get().getLabel() : null;
    }

//    private String getObjectPropertyDomainLabelById(String objectPropertyId){
//        Optional<OwlObjectProperty> objectPropertyProperty = _objectProperties
//                .stream()
//                .filter(x -> x.getId().equals(objectPropertyId))
//                .findFirst();
//        String domain = objectPropertyProperty.isPresent() ? objectPropertyProperty.get().getDomain() : null;
//
//        return domain == null ? null : getClassesLabelByUri(domain);
//    }
}
