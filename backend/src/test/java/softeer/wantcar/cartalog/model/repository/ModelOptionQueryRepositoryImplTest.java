package softeer.wantcar.cartalog.model.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@SpringBootTest
class ModelOptionQueryRepositoryImplTest {

    @Autowired
    ModelOptionQueryRepositoryImpl modelOptionQueryRepository;

    @Test
    void test() {

        modelOptionQueryRepository.findByModelId(1L);


    }

}