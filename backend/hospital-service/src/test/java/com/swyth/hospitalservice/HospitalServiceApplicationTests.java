package com.swyth.hospitalservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
@EnableTestBinder // Decouple your tests from a binder like Kafka
class HospitalServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
