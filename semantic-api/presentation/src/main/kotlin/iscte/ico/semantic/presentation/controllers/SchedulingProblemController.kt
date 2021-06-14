package iscte.ico.semantic.presentation.controllers

import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.application.model.SchedulingProblemRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RestController
@RequestMapping("/scheduling-problem")
class SchedulingProblemController {

    @Autowired
    private lateinit var _owlService: OwlService

    @PostMapping(produces = arrayOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
    @CrossOrigin(origins = ["*"])
    fun post(@RequestBody request : SchedulingProblemRequest) : ByteArray {

        return _owlService.addSchedulingProblemIndividual(request)

    }
}