package id.my.hendisantika.springbootwebfluxparteventapiexample.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-webflux-part-event-api-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/19/24
 * Time: 09:46
 * To change this template use File | Settings | File Templates.
 */
@WebFluxTest
public class FileUploadControllerTest {

    @Autowired
    private FileUploadController fileUploadController;

    @Autowired
    private WebTestClient client;


    @Test
    public void testHandleSimpleFileUpload() {
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("name", "test");
        multipartBodyBuilder.part("file", new ClassPathResource("spring.png"), MediaType.IMAGE_PNG);
        var multipartBody = multipartBodyBuilder.build();
        this.client
                .post().uri("/simple-form-upload")
                .contentType(MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartBody))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("test")
                .jsonPath("$.filename").isEqualTo("spring.png");
    }
}
