package perobobbot.rest.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping(value = "/{type}:{groupId}:{artifactId}", produces = "application/zip")
    public void getPluginTemplate(
            @PathVariable("type") String type,
            @PathVariable("groupId") String groupId,
            @PathVariable("artifactId") String artifactId,
            HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\""+artifactId+".zip\"");

        final Path path = templateGenerator.generate(type,groupId,artifactId);

        final var zipOutputStream = new ZipOutputStream(response.getOutputStream());
        Zipper.zipper(path).accept(zipOutputStream);
        zipOutputStream.close();
    }



}
