package iscte.ico.semantic.application.interfaces

import iscte.ico.semantic.application.model.AlgorithmsProblems
import iscte.ico.semantic.application.model.Properties

interface OwlService {

    fun getAlgorithmsProblems(properties: Properties) : AlgorithmsProblems
}