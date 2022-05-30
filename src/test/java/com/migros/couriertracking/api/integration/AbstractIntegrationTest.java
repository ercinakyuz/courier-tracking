package com.migros.couriertracking.api.integration;


import com.migros.couriertracking.CourierTrackingApplication;
import com.migros.couriertracking.infrastructure.AbstractTest;
import com.migros.couriertracking.infrastructure.util.ObjectMapperContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CourierTrackingApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AbstractIntegrationTest extends AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    protected static ObjectMapperContext objectMapperContext;


    @BeforeAll
    public static void beforeAllIntegration() {
        objectMapperContext = new ObjectMapperContext();
    }

}
