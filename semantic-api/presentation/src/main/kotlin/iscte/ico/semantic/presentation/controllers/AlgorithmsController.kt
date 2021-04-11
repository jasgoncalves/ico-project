package iscte.ico.semantic.presentation.controllers

import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.application.model.AlgorithmsProblems
import iscte.ico.semantic.application.model.Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/algorithms")
@Controller
class AlgorithmsController{

    @Autowired
    lateinit var owlService: OwlService

    @GetMapping
    fun get(
        @RequestParam(required = true, name = "dealsWithHeavyProcessingEvaluationFunctions") dealsWithHeavyProcessingEvaluationFunctions: Boolean,
        @RequestParam(required = true, name = "maxObjectivesAlgorithmIsAbleToDealWith") maxObjectivesAlgorithmIsAbleToDealWith : Integer ,
        @RequestParam(required = true, name = "minObjectivesAlgorithmIsAbleToDealWith") minObjectivesAlgorithmIsAbleToDealWith : Integer) : AlgorithmsProblems =
            owlService.getAlgorithmsProblems(Properties(dealsWithHeavyProcessingEvaluationFunctions,maxObjectivesAlgorithmIsAbleToDealWith,minObjectivesAlgorithmIsAbleToDealWith))

}