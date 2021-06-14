package iscte.ico.semantic.application.interfaces

import iscte.ico.semantic.application.model.SchedulingProblemRequest
import iscte.ico.semantic.domain.entities.OwlClass
import iscte.ico.semantic.domain.entities.OwlDatatypeProperty
import iscte.ico.semantic.domain.entities.OwlIndividual
import iscte.ico.semantic.domain.entities.OwlObjectProperty
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

interface OntologyService {

    fun getClasses() : List<OwlClass>
    fun getIndividuals() : List<OwlIndividual>
    fun getDatatypeProperties() : List<OwlDatatypeProperty>
    fun getObjectProperties() : List<OwlObjectProperty>
    @Throws(IOException::class)
    fun addSchedulingProblemIndividual(schedulingProblemRequest : SchedulingProblemRequest?) : ByteArray
}