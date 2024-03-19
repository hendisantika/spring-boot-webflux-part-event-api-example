package id.my.hendisantika.springbootwebfluxparteventapiexample.controller;

import id.my.hendisantika.springbootwebfluxparteventapiexample.model.FileUploadCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-webflux-part-event-api-example
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/19/24
 * Time: 09:44
 * To change this template use File | Settings | File Templates.
 */
@RestController
@Slf4j
@SuppressWarnings("unused")
public class FileUploadController {
    @PostMapping(value = "simple-form-upload-mvc", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> handleFileUploadForm(
            @RequestPart("file") MultipartFile file)
            throws IOException {

        log.info("handling request parts: {}", file);

        try {

            File f = new ClassPathResource("").getFile();
            final Path path = Paths.get(
                    f.getAbsolutePath() + File.separator + "static" + File.separator + "image");

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path filePath = path.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/image/")
                    .path(file.getOriginalFilename())
                    .toUriString();

            var result = Map.of(
                    "filename", file.getOriginalFilename(),
                    "fileUri", fileUri
            );
            return ok().body(result);
        } catch (IOException e) {

            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "simple-form-upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> handleFileUploadForm(FileUploadCommand form) {
        log.info("uploading form data: {}", form);

        var result = Map.of(
                "name", form.getName(),
                "filename", form.getFile().filename()
        );
        return ok().body(result);
    }

    @PostMapping(value = "upload-with-request-parts", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> handleRequestParts(@RequestPart("name") String name,
                                                                  @RequestPart("file") FilePart file) {
        log.info("handling request parts: {}, {}", name, file);
        var result = Map.of(
                "name", name,
                "filename", file.filename()
        );
        return ok().body(result);
    }

    @PostMapping(value = "upload-with-multi-value-map", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Mono<List<String>>> handleMultiValueMap(
            @RequestBody Mono<MultiValueMap<String, Part>> parts) {
        log.debug("handling multi values: {}", parts);
        var partNames = parts.map
                        (p -> p.keySet().stream().map(key -> p.getFirst(key).name()).toList())
                .log();
        return ok().body(partNames);
    }

}
