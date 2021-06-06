package iscte.ico.semantic.presentation.controllers

import iscte.ico.semantic.application.interfaces.DatabaseService
import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.application.model.QueryParameters
import iscte.ico.semantic.presentation.model.Error
import iscte.ico.semantic.presentation.model.ErrorResponseModel
import iscte.ico.semantic.presentation.model.QueryRequestModel
import iscte.ico.semantic.presentation.model.ResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RestController
@RequestMapping("/sqrwl/query")
class SQRWLController {

    @Autowired
    private lateinit var _owlService: OwlService

    @Autowired
    private lateinit var _databaseService: DatabaseService

    @PostMapping("/run")
    @CrossOrigin(origins = ["*"])
    fun run(@RequestBody request : QueryRequestModel) : ResponseModel {
        val listQueryParameters = mutableListOf<QueryParameters>()
        request.queryParameters.forEach{
            listQueryParameters.add(QueryParameters(it.entityType, it.entity, it.name, it.isOrderedBy, it.isColumnShowed, it.args))
        }
        return ResponseModel(
            _owlService.executeQuery(listQueryParameters),
            Error(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Success")
        )
    }

    @PostMapping
    @CrossOrigin(origins = ["*"])
    fun post(@RequestBody request : QueryRequestModel) : ResponseModel {
        val name = request.name
        val listQueryParameters = mutableListOf<QueryParameters>()
        request.queryParameters.forEach{
            listQueryParameters.add(QueryParameters(it.entityType, it.entity, it.name, it.isOrderedBy, it.isColumnShowed, it.args))
        }
        _databaseService.createQuery(name, listQueryParameters)
        return ResponseModel(
            _owlService.executeQuery(listQueryParameters),
            Error(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), "Success")
        )
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = ["*"])
    fun getByID(@PathVariable("id") queryID : UUID) : ResponseModel {
        return ResponseModel(
            _databaseService.getQuery(queryID),
            Error(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Success")
        )
    }

    @PutMapping("/{id}")
    @CrossOrigin(origins = ["*"])
    fun updateByID(@PathVariable("id") queryID : UUID, @RequestBody request : QueryRequestModel) : ResponseModel {
        val name = request.name
        val listQueryParameters = mutableListOf<QueryParameters>()
        request.queryParameters.forEach{
            listQueryParameters.add(QueryParameters(it.entityType, it.entity, it.name, it.isOrderedBy, it.isColumnShowed, it.args))
        }
        _databaseService.updateQuery(queryID, name, listQueryParameters)
        return ResponseModel(
            _owlService.executeQuery(listQueryParameters),
            Error(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Success")
        )
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = ["*"])
    fun deleteByID(@PathVariable("id") queryID : UUID) : ErrorResponseModel {
        _databaseService.deleteQuery(queryID)
        return ErrorResponseModel(
            Error(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.toString(), "Success")
        )
    }

    @GetMapping
    @CrossOrigin(origins = ["*"])
    fun get() : ResponseModel {
        return ResponseModel(
            _databaseService.getQueries(),
            Error(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Success")
        )
    }

}