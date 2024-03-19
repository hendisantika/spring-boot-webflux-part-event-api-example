package id.my.hendisantika.springbootwebfluxparteventapiexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

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
    FileUploadController fileUploadController;

    @Autowired
    WebTestClient client;


}
