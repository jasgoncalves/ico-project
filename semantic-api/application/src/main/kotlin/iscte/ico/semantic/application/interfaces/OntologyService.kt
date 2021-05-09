package iscte.ico.semantic.application.interfaces

import iscte.ico.semantic.domain.entities.OwlClass
import iscte.ico.semantic.domain.entities.OwlDatatypeProperty
import iscte.ico.semantic.domain.entities.OwlIndividual
import iscte.ico.semantic.domain.entities.OwlObjectProperty

interface OntologyService {

    fun getClasses() : List<OwlClass>
    fun getIndividuals() : List<OwlIndividual>
    fun getDatatypeProperties() : List<OwlDatatypeProperty>
    fun getObjectProperties() : List<OwlObjectProperty>
}