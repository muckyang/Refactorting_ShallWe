package ShallWe.Refactoring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(false)
public class OrderTest {

    @Autowired
    private EntityManager em;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeEach
    void createEM(){
        logger.trace("*************** Order Test Start *******************");
    }


    @Test
    public void saveOrder() throws Exception {

    }

}
