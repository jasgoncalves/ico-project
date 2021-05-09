package iscte.ico.semantic.infrastructure.services;

import iscte.ico.semantic.application.interfaces.OntologyService;
import iscte.ico.semantic.domain.entities.OwlClass;
import iscte.ico.semantic.domain.entities.OwlDatatypeProperty;
import iscte.ico.semantic.domain.entities.OwlIndividual;
import iscte.ico.semantic.domain.entities.OwlObjectProperty;
import org.apache.jena.ontology.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.RDFS;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OntologyServiceImpl implements OntologyService {

    private OntModel _model;
    private Logger _logger;

    public OntologyServiceImpl(Logger logger) {
        _model = ModelFactory.createOntologyModel();
        _model.read(getClass().getClassLoader().getResource("PMOEA.owl").toString());
        _logger = logger;
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
            String label = ontClass.getPropertyValue(RDFS.label) == null
                    ? ontClass.getLocalName()
                    : ontClass.getPropertyValue(RDFS.label).toString().replace("@en", "");
            String description = ontClass.getPropertyValue(RDFS.comment) == null
                    ? ""
                    : ontClass.getPropertyValue(RDFS.comment).toString();

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
            String label = individual.getPropertyValue(RDFS.label) == null
                    ? individual.getLocalName()
                    : individual.getPropertyValue(RDFS.label).toString().replace("@en", "");
            String description = individual.getPropertyValue(RDFS.comment) == null
                    ? ""
                    : individual.getPropertyValue(RDFS.comment).toString();

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
            String dataType = datatypeProperty.getPropertyValue(RDFS.range) == null
                    ? null
                    : datatypeProperty.getPropertyValue(RDFS.range).toString().replace("http://www.w3.org/2001/XMLSchema#", "");
            String label = datatypeProperty.getPropertyValue(RDFS.label) == null
                    ? datatypeProperty.getLocalName()
                    : datatypeProperty.getPropertyValue(RDFS.label).toString().replace("@en", "");
            String description = datatypeProperty.getPropertyValue(RDFS.comment) == null
                    ? ""
                    : datatypeProperty.getPropertyValue(RDFS.comment).toString();

            if(id != null)
                listDatatypeProperties.add(new OwlDatatypeProperty( id, label, dataType, uri, description));
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
            String label = objectProperty.getPropertyValue(RDFS.label) == null
                    ? objectProperty.getLocalName()
                    : objectProperty.getPropertyValue(RDFS.label).toString().replace("@en", "");
            String description = objectProperty.getPropertyValue(RDFS.comment) == null
                    ? ""
                    : objectProperty.getPropertyValue(RDFS.comment).toString();

            if(id != null)
                listObjectProperties.add(new OwlObjectProperty( id, label, uri, description));
        }
        return listObjectProperties;
    }

}
