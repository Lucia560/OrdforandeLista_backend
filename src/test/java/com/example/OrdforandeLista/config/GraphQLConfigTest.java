package com.example.OrdforandeLista.config;

import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(GraphQLConfig.class)

class GraphQLConfigTest {

    private final RuntimeWiringConfigurer runtimeWiringConfigurer;

    @Autowired
    public GraphQLConfigTest(RuntimeWiringConfigurer runtimeWiringConfigurer) {
        this.runtimeWiringConfigurer = runtimeWiringConfigurer;
    }

    @Test
    void testRuntimeWiringConfigurerRegistersScalars() {
        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
        runtimeWiringConfigurer.configure(builder);
        RuntimeWiring wiring = builder.build();
        Map<String, GraphQLScalarType> scalars = wiring.getScalars();

        assertAll(
                () -> assertTrue(scalars.containsKey("DateTime"), "DateTime scalar is missing"),
                () -> assertTrue(scalars.containsKey("Url"), "Url scalar is missing"),
                () -> assertTrue(scalars.containsKey("Date"), "Date scalar is missing"),
                () -> assertTrue(scalars.containsKey("LocalTime"), "LocalTime scalar is missing"),
                () -> assertTrue(scalars.containsKey("JSON"), "Json scalar is missing")

        );
    }
}
