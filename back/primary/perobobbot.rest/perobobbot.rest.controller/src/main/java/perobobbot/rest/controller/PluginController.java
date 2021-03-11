package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import perobobbot.lang.TemplateGenerator;
import perobobbot.lang.Zipper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;

@RequestMapping("/api/plugin")
@RestController
@Log4j2
@RequiredArgsConstructor
public class PluginController {

    private final @NonNull TemplateGenerator templateGenerator;

    @GetMapping(value = "/{groupId}:{artifactId}", produces = "application/zip")
    public void getPluginTemplate(
            @PathVariable("groupId") String groupId,
            @PathVariable("artifactId") String artifactId,
            HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\""+artifactId+".zip\"");

        final Path path = templateGenerator.generate(groupId,artifactId);

        final var zipOutputStream = new ZipOutputStream(response.getOutputStream());
        Zipper.zipper(path).accept(zipOutputStream);
        zipOutputStream.close();
    }



}
