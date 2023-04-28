package com.corporation.apiclient.integrationtests.controller.withjson;

import com.corporation.apiclient.config.TestConfig;
import com.corporation.apiclient.integrationtests.dto.AdressDTO;
import com.corporation.apiclient.integrationtests.dto.ClientDTO;
import com.corporation.apiclient.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class) // Aplicando Minha Ordem para executar os testes
public class AdressControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static AdressDTO adressDTO;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        adressDTO = new AdressDTO();
    }

    //Test da Conexao com o Site da Documentação
    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_JP)
                .setBasePath("/adress")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .pathParam("id", 1L)
                .body(adressDTO)
                .when()
                .post("{id}")
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        AdressDTO createdAdressDTO = objectMapper.readValue(content, AdressDTO.class);
        adressDTO = createdAdressDTO;

        Assertions.assertNotNull(createdAdressDTO);
        Assertions.assertNotNull(createdAdressDTO.getId());
        Assertions.assertNotNull(createdAdressDTO.getCity());
        Assertions.assertNotNull(createdAdressDTO.getStreet());
        Assertions.assertNotNull(createdAdressDTO.getState());
        Assertions.assertNotNull(createdAdressDTO.getNumber());
        Assertions.assertNotNull(createdAdressDTO.getDistrict());


        Assertions.assertEquals("Ubatuba", createdAdressDTO.getCity());
        Assertions.assertEquals("Rua das Flores", createdAdressDTO.getStreet());
        Assertions.assertEquals("SP", createdAdressDTO.getState());
        Assertions.assertEquals("50", createdAdressDTO.getNumber());
        Assertions.assertEquals("Mirim", createdAdressDTO.getDistrict());
    }

    @Test
    @Order(2)
    public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, "https://urlerrada.com.br")
                .setBasePath("/adress")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .pathParam("id", 1L)
                .body(adressDTO)
                .when()
                .post("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();


        Assertions.assertNotNull(content);
        Assertions.assertEquals("Invalid CORS request", content);
    }

    @Test
    @Order(3)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_JP)
                .setBasePath("/adress")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .pathParam("id", adressDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        AdressDTO createdAdressDTO = objectMapper.readValue(content, AdressDTO.class);
        adressDTO = createdAdressDTO;

        Assertions.assertNotNull(createdAdressDTO);
        Assertions.assertNotNull(createdAdressDTO.getId());
        Assertions.assertNotNull(createdAdressDTO.getCity());
        Assertions.assertNotNull(createdAdressDTO.getStreet());
        Assertions.assertNotNull(createdAdressDTO.getState());
        Assertions.assertNotNull(createdAdressDTO.getNumber());
        Assertions.assertNotNull(createdAdressDTO.getDistrict());


        Assertions.assertEquals("Ubatuba", createdAdressDTO.getCity());
        Assertions.assertEquals("Rua das Flores", createdAdressDTO.getStreet());
        Assertions.assertEquals("SP", createdAdressDTO.getState());
        Assertions.assertEquals("50", createdAdressDTO.getNumber());
        Assertions.assertEquals("Mirim", createdAdressDTO.getDistrict());
    }

    @Test
    @Order(4)
    public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, "https://www.urlerrada.com.br")
                .setBasePath("/adress")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given().spec(specification)
                .contentType(TestConfig.CONTENT_TYPE_JSON)
                .pathParam("id", adressDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        Assertions.assertNotNull(content);
        Assertions.assertEquals("Invalid CORS request", content);
    }

    private void mockPerson() {
        adressDTO.setCity("Ubatuba");
        adressDTO.setDistrict("Mirim");
        adressDTO.setState("SP");
        adressDTO.setStreet("Rua das Flores");
        adressDTO.setNumber("50");

    }

}