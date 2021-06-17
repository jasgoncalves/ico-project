package iscte.ico.semantic.presentation.controllers

import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.presentation.model.Error
import iscte.ico.semantic.presentation.model.ResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RestController
@RequestMapping("/classes")
class ClassesController {

    @Autowired
    private lateinit var _owlService: OwlService

    @GetMapping
    @CrossOrigin(origins = ["*"])
    fun get() : ResponseModel {

        return ResponseModel(
            _owlService.getClasses(),
            Error(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Success")
        )
    }
}