import iscte.ico.semantic.infrastructure.services.OntologyServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public class OntologyServiceImplTest {

    @Test
    public void test() throws FileNotFoundException {

        OntologyServiceImpl ontologyService = new OntologyServiceImpl(LoggerFactory.getLogger(OntologyServiceImplTest.class));
//        ontologyService.addNewIndividual();

    }
}
