package id.my.hendisantika.springbootwebfluxparteventapiexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-webflux-part-event-api-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/19/24
 * Time: 09:41
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadCommand {
    private String name;
    private FilePart file;
}
