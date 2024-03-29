package perobobbot.server.plugin.template;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import jplugman.api.Version;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.server.plugin.BotVersionedService;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class PluginInfo {

    private final @NonNull Version applicationVersion;

    @Getter
    private final @NonNull String moduleName;

    @Getter
    private final @NonNull String packageName;

    private final @NonNull ImmutableSet<String> requiredModuleNames;

    @Getter
    private final @NonNull ImmutableList<ServiceWrapper> services;

    public @NonNull String getApplicationVersion() {
        return applicationVersion.toString();
    }


    public @NonNull Set<String> getModuleNames() {
        return Stream.concat(
                requiredModuleNames.stream(),
                services.stream()
                        .map(ServiceWrapper::getModuleName)
        ).collect(Collectors.toSet());
    }

    public @NonNull Set<String> getServiceArtifactIds() {
        return services.stream()
                       .map(ServiceWrapper::getArtifactId)
                       .collect(Collectors.toSet());
    }

    @RequiredArgsConstructor
    public static class ServiceWrapper {

        private final @NonNull BotVersionedService service;

        public @NonNull String getImport() {
            return service.getServiceType().getPackageName() + "." + getSimpleClassName();
        }

        public @NonNull String getModuleName() {
            return service.getServiceType().getModule().getName();
        }

        public @NonNull String getArtifactId() {
            return service.getServiceType().getModule().getName().replaceAll("\\.", "-");
        }

        public @NonNull String getSimpleClassName() {
            return service.getServiceType().getSimpleName();
        }

        public @NonNull String getVariableName() {
            final var name = getSimpleClassName();
            if (name.toUpperCase().equals(name)) {
                return name;
            }
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
        }

        public int getMajorVersion() {
            return service.getMajorVersion();
        }

    }
}
