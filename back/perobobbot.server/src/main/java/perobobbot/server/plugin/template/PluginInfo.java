package perobobbot.server.plugin.template;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import jplugman.api.Version;
import jplugman.api.VersionedServiceClass;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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

        private final @NonNull VersionedServiceClass<?> service;

        public @NonNull String getImport() {
            return service.getServiceClass().getPackageName()+"."+getSimpleClassName();
        }

        public @NonNull String getModuleName() {
            return service.getServiceClass().getModule().getName();
        }

        public @NonNull String getArtifactId() {
            return service.getServiceClass().getModule().getName().replaceAll("\\.","-");
        }

        public @NonNull String getSimpleClassName() {
            return service.getServiceClass().getSimpleName();
        }

        public @NonNull String getVariableName() {
            final var name = getSimpleClassName();
            if (name.toUpperCase().equals(name)) {
                return name;
            }
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
        }

        public @NonNull String getVersionAsString() {
            return service.getVersion().toString();
        }

    }
}
