package iscte.ico.semantic.presentation.controllers

import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.application.model.Properties
import iscte.ico.semantic.presentation.model.ErrorModel
import iscte.ico.semantic.presentation.model.ErrorResponseModel
import iscte.ico.semantic.presentation.model.ResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException

import org.springframework.web.context.request.WebRequest




@Controller
@RestController
@RequestMapping("/algorithms")
class AlgorithmsController{

    @Autowired
    private lateinit var _owlService: OwlService

    @GetMapping
    fun get(
        @RequestParam(required = true, name = "dealsWithHeavyProcessingEvaluationFunctions") dealsWithHeavyProcessingEvaluationFunctions: Boolean,
        @RequestParam(required = true, name = "maxObjectivesAlgorithmIsAbleToDealWith") maxObjectivesAlgorithmIsAbleToDealWith : Integer ,
        @RequestParam(required = true, name = "minObjectivesAlgorithmIsAbleToDealWith") minObjectivesAlgorithmIsAbleToDealWith : Integer) : ResponseModel {

        return ResponseModel(
            _owlService.getAlgorithmsProblems(
                Properties(
                    dealsWithHeavyProcessingEvaluationFunctions ,
                    maxObjectivesAlgorithmIsAbleToDealWith ,
                    minObjectivesAlgorithmIsAbleToDealWith
                )
            ),
            ErrorModel(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Success")
        )
    }
}