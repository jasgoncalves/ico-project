package iscte.ico.semantic.infrastructure.services

import iscte.ico.semantic.application.interfaces.OwlService
import iscte.ico.semantic.application.model.AlgorithmsProblems
import iscte.ico.semantic.application.model.Properties
import org.apache.jena.query.QueryExecutionFactory
import org.apache.jena.query.QueryFactory
import org.apache.jena.util.FileManager
import org.slf4j.LoggerFactory



class OwlServiceImpl : OwlService {

    fun test(){

        val logger = LoggerFactory.getLogger(OwlService::class.java)

        FileManager.get().addLocatorClassLoader(OwlServiceImpl::class.java.classLoader)
        val model = FileManager.get().loadModel("C:\\Users\\jorge\\OneDrive\\Ambiente de Trabalho\\ISCTE\\MEI_1stYear_2stSemester\\ICO\\ico-project\\semantic-api\\infrastructure\\src\\main\\resources\\PMOEA.owl")

//        model.write(System.out, "TURTLE")



        val queryString =
                "PREFIX PMOEA: <http://www.w3.org/2002/07/owl#>\n" +
                        "SELECT * WHERE {\n" +
                        "  ?sub ?pred ?obj .\n" +
                        "} \n"
        val query = QueryFactory.create(queryString)
        val qexec = QueryExecutionFactory.create(query, model)

        try{
            val results = qexec.execSelect()
            while (results.hasNext()){
                println(results.nextSolution())
            }
        }
        finally{
            qexec.close()
        }

    }

    override fun getAlgorithmsProblems(properties : Properties) : AlgorithmsProblems{

        TODO("Not Yet Implemented")


    }
}