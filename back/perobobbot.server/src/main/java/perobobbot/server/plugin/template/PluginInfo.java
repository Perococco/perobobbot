package perobobbot.server.plugin.template;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import jplugman.api.Version;
import jplugman.api.VersionedService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.server.plugin.BotVersionedService;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PluginInfo {

    private final @NonNull Version applicationVersion;

    @Getter
    private final @NonNull String moduleName;

    @Getter
    private final @NonNull String packageName;

    @Getter
    private final @NonNull ImmutableList<ServiceWrapper> services;

    public @NonNull String getApplicationVersion() {
        return applicationVersion.toString();
    }

    public @NonNull Set<String> getServiceModuleNames() {
        return services.stream()
                       .map(ServiceWrapper::getModuleName)
                       .collect(Collectors.toSet());
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
            return service.getServiceType().getPackageName()+"."+getSimpleClassName();
        }

        public @NonNull String getModuleName() {
            return service.getServiceType().getModule().getName();
        }

        public @NonNull String getArtifactId() {
            return service.getServiceType().getModule().getName().replaceAll("\\.","-");
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
