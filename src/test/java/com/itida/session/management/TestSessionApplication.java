package com.itida.session.management;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
@Import(TestContainerConfig.class)
public class TestSessionApplication {

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry,
                              @Autowired MySQLContainer<?> mySQLContainer) {
        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
        dynamicPropertyRegistry.add("spring.jpa.generate-ddl", () -> "true");
        dynamicPropertyRegistry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    public static void main(String[] args) {
        SpringApplication.from(SessionManagementApplication::main)
                .with(TestSessionApplication.class).run(args);
    }
}