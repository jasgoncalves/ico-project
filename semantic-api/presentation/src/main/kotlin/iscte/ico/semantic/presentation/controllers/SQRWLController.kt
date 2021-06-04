package iscte.ico.semantic.presentation.controllers

import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.application.model.QueryParameters
import iscte.ico.semantic.presentation.model.Error
import iscte.ico.semantic.presentation.model.QueryRequestModel
import iscte.ico.semantic.presentation.model.ResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RestController
@RequestMapping("/sqrwl/query")
class SQRWLController {

    @Autowired
    private lateinit var _owlService: OwlService

    @PostMapping
    @CrossOrigin(origins = ["*"])
    fun post(@RequestBody request : QueryRequestModel) : ResponseModel {
        var listQueryParamaters = mutableListOf<QueryParameters>()
        request.queryParameters.forEach{
            listQueryParamaters.add(QueryParameters(it.entityType, it.entity, it.name, it.isOrderedBy, it.args))
        }
        return ResponseModel(
            _owlService.executeQuery(listQueryParamaters),
            Error(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Success")
        )
    }

    @GetMapping
    @CrossOrigin(origins = ["*"])
    fun get(@RequestParam("queryName") queryName : String) : ResponseModel {
        return ResponseModel(
            _owlService.executeQuery(queryName),
            Error(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Success")
        )
    }

}