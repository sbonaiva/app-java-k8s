package com.bonaiva.app.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.bonaiva.app")
class LayeredArchitectureTest {

    @ArchTest
    static final ArchRule layeredArchitectureRule = layeredArchitecture().consideringAllDependencies()
            .layer("Controller").definedBy("com.bonaiva.app.controller..")
            .layer("UseCase").definedBy("com.bonaiva.app.usecase..")
            .layer("Domain").definedBy("com.bonaiva.app.domain..")
            .layer("Integration").definedBy("com.bonaiva.app.integration..")
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("UseCase").mayOnlyBeAccessedByLayers("Controller", "Integration")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Controller", "UseCase", "Integration")
            .whereLayer("Integration").mayNotBeAccessedByAnyLayer()
            .ignoreDependency(
                    "com.bonaiva.app.controller.handler.ControllerExceptionHandler",
                    "com.bonaiva.app.integration.exception.IntegrationException"
            );
}
