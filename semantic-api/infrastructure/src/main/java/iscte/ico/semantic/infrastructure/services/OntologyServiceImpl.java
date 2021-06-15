package iscte.ico.semantic.infrastructure.services;

import iscte.ico.semantic.application.interfaces.OntologyService;
import iscte.ico.semantic.application.model.PropertyType;
import iscte.ico.semantic.application.model.SchedulingProblemRequest;
import iscte.ico.semantic.domain.entities.OwlClass;
import iscte.ico.semantic.domain.entities.OwlDatatypeProperty;
import iscte.ico.semantic.domain.entities.OwlIndividual;
import iscte.ico.semantic.domain.entities.OwlObjectProperty;
import iscte.ico.semantic.infrastructure.InfrastructureConfig;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OntologyServiceImpl implements OntologyService {

    private OntModel _model;
    private Logger _logger;

    @Autowired
    public OntologyServiceImpl(Logger logger) {
        _logger = logger;
        _model = ModelFactory.createOntologyModel();
        if(InfrastructureConfig.USE_URL)
            _model.read(InfrastructureConfig.ONTOLOGY_URL);
        else
            _model.read(getClass().getClassLoader().getResource(InfrastructureConfig.ONTOLOGY_RESOURCE_FILE).toString());
    }

    @Override
    public List<OwlClass> getClasses() {

        ExtendedIterator classes = _model.listClasses();
        List<OwlClass> listClasses = new ArrayList<>();

        while (classes.hasNext())
        {
            OntClass ontClass = (OntClass) classes.next();
            String id = ontClass.getLocalName();
            String uri = ontClass.getURI();
            String label = ontClass.getLabel("EN") == null
                    ? ontClass.getLocalName()
                    : ontClass.getLabel("EN");
            String description = ontClass.getComment("EN");

            if(id != null)
                listClasses.add(new OwlClass( id, label, uri, description));
        }
        return listClasses;
    }

    @Override
    public List<OwlIndividual> getIndividuals() {

        ExtendedIterator individuals = _model.listIndividuals();
        List<OwlIndividual> listIndividuals = new ArrayList<>();

        while (individuals.hasNext())
        {
            Individual individual = (Individual) individuals.next();
            String id = individual.getLocalName();
            String uri = individual.getURI();
            String label = individual.getLabel("EN") == null
                ? individual.getLocalName()
                : individual.getLabel("EN");
            String description = individual.getComment("EN");

            if(id != null)
                listIndividuals.add(new OwlIndividual( id, label, uri, description));
        }
        return listIndividuals;
    }

    @Override
    public List<OwlDatatypeProperty> getDatatypeProperties() {

        ExtendedIterator datatypeProperties = _model.listDatatypeProperties();
        List<OwlDatatypeProperty> listDatatypeProperties = new ArrayList<>();

        while (datatypeProperties.hasNext())
        {
            DatatypeProperty datatypeProperty = (DatatypeProperty) datatypeProperties.next();
            String id = datatypeProperty.getLocalName();
            String uri = datatypeProperty.getURI();
            String domainId = datatypeProperty.getDomain() == null
                    ? null
                    : datatypeProperty.getDomain().getLocalName();
            String domainLabel = datatypeProperty.getDomain() == null
                    ? null
                    : datatypeProperty.getDomain().getLabel("EN");
            String dataType = datatypeProperty.getRange() == null
                    ? null
                    : datatypeProperty.getRange().getLocalName();
            String label = datatypeProperty.getLabel("EN") == null
                    ? datatypeProperty.getLocalName()
                    : datatypeProperty.getLabel("EN");
            String description = datatypeProperty.getComment("EN");

            if(id != null)
                listDatatypeProperties.add(new OwlDatatypeProperty( id, label, dataType, domainId, domainLabel, uri, description));
        }
        return listDatatypeProperties;
    }

    @Override
    public List<OwlObjectProperty> getObjectProperties() {

        ExtendedIterator objectProperties = _model.listObjectProperties();
        List<OwlObjectProperty> listObjectProperties = new ArrayList<>();

        while (objectProperties.hasNext())
        {
            ObjectProperty objectProperty = (ObjectProperty) objectProperties.next();
            String id = objectProperty.getLocalName();
            String uri = objectProperty.getURI();
            String label = objectProperty.getLabel("EN") == null
                    ? objectProperty.getLocalName()
                    : objectProperty.getLabel("EN");
            String description = objectProperty.getComment("EN");
            String domainId = objectProperty.getDomain() == null
                    ? null
                    : objectProperty.getDomain().getLocalName();
            String domainLabel = objectProperty.getDomain() == null
                    ? null
                    : objectProperty.getDomain().getLabel("EN");
            String rangeId = objectProperty.getRange() == null
                    ? null
                    : objectProperty.getRange().getLocalName();

            if(id != null)
                listObjectProperties.add(new OwlObjectProperty( id, label, domainId, domainLabel, rangeId, uri, description));
        }
        return listObjectProperties;
    }

    @Override
    public byte[] addSchedulingProblemIndividual(SchedulingProblemRequest schedulingProblemRequest) throws IOException {

        String scheduling = _model.getNsPrefixURI("scheduling");
        String owl = _model.getNsPrefixURI("owl");
        OntClass c = _model.getOntClass(owl+"NamedIndividual");

        Individual individual = _model.createIndividual( scheduling + schedulingProblemRequest.getName(), c );
        individual.addRDFType(_model.getOntClass(scheduling+"SchedulingProblem"));

        Arrays.stream(schedulingProblemRequest.getSchedulingProblem().getProperties()).forEach(
                element-> {individual.addProperty(_model.getDatatypeProperty(scheduling+element.getName()),element.getValue());}
        );

        Arrays.stream(schedulingProblemRequest.getMachines()).forEach(
                element -> {
                    Individual machine = _model.createIndividual( scheduling + element.getName(), c );
                    machine.addRDFType(_model.getOntClass(scheduling+"Machine"));
                    Arrays.stream(element.getProperties()).forEach(
                            e -> {machine.addProperty(_model.getDatatypeProperty(scheduling+e.getName()),e.getValue());}
                    );

                }
        );

        Individual order = _model.createIndividual( scheduling + schedulingProblemRequest.getOrder().getName(), c );
        order.addRDFType(_model.getOntClass(scheduling+"Order"));

        Arrays.stream(schedulingProblemRequest.getOrder().getProperties()).forEach(
                element-> {order.addProperty(_model.getDatatypeProperty(scheduling+element.getName()),element.getValue());}
        );

        Arrays.stream(schedulingProblemRequest.getJobFamilies()).forEach(
                element -> {
                    Individual jobFamily = _model.createIndividual( scheduling + element.getName(), c );
                    jobFamily.addRDFType(_model.getOntClass(scheduling+"JobFamily"));
                }
        );

        Arrays.stream(schedulingProblemRequest.getJobs()).forEach(
                element -> {
                    Individual job = _model.createIndividual( scheduling + element.getName(), c );
                    job.addRDFType(_model.getOntClass(scheduling+"Job"));
                    Arrays.stream(element.getProperties()).forEach(
                            e -> {
                                if (e.getType() == PropertyType.DatatypeProperty) {
                                    job.addProperty(_model.getDatatypeProperty(scheduling + e.getName()), e.getValue());
                                }
                                if (e.getType() == PropertyType.ObjectProperty) {
                                    job.addProperty(_model.getObjectProperty(scheduling + e.getName()), e.getValue());
                                }
                            }
                    );

                }
        );

        Arrays.stream(schedulingProblemRequest.getTasks()).forEach(
                element -> {
                    Individual task = _model.createIndividual( scheduling + element.getName(), c );
                    task.addRDFType(_model.getOntClass(scheduling+"Task"));
                    Arrays.stream(element.getProperties()).forEach(
                            e -> {
                                if (e.getType() == PropertyType.DatatypeProperty) {
                                    task.addProperty(_model.getDatatypeProperty(scheduling + e.getName()), e.getValue());
                                }
                                if (e.getType() == PropertyType.ObjectProperty) {
                                    task.addProperty(_model.getObjectProperty(scheduling + e.getName()), e.getValue());
                                }
                            }
                    );

                }
        );

//        Individual objectiveFunction = _model.createIndividual( scheduling + schedulingProblemRequest.getObjectiveFunction().getName(), c );
//        objectiveFunction.addRDFType(_model.getOntClass(scheduling+"ObjectiveFunction"));

        Arrays.stream(schedulingProblemRequest.getObjectiveFunction().getProperties()).forEach(
                element-> {individual.addProperty(_model.getObjectProperty(scheduling+"hasObjectiveFunction"),element.getName());}
        );

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        _model.write(out,"RDF/XML");

        return out.toByteArray();

    }

}
