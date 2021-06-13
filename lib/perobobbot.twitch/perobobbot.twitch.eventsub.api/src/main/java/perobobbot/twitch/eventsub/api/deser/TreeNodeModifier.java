package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeNodeModifier {

    private static final Set<String> SUFFIXES = Set.of("id", "login", "name");

    public static @NonNull void modify(@NonNull ObjectNode objectNode) {
        new TreeNodeModifier(objectNode).modify();
    }

    private final @NonNull ObjectNode source;

    private Map<String, Integer> candidateFieldNames;
    private Set<String> validUserInfo;
    private Set<String> fieldNamesUsedInUserInfo;

    private void modify() {
        this.countUserInfoCandidats();
        this.filterValidUserInfo();
        this.createSetOfFieldNamesUsedInUserInfo();
        this.addUserInfoFields();
        this.removeFieldsInUserInfo();
    }

    private void countUserInfoCandidats() {
        candidateFieldNames = new HashMap<>();
        final var itr = source.fieldNames();
        while (itr.hasNext()) {
            getUserInfoPrefix(itr.next()).ifPresent(p -> candidateFieldNames.merge(p, 1, Integer::sum));
        }
    }

    private @NonNull Optional<String> getUserInfoPrefix(@NonNull String fieldName) {
        return SUFFIXES.stream().map(s -> extractUserField(fieldName, s))
                       .flatMap(Optional::stream)
                       .findAny();
    }

    private void filterValidUserInfo() {
        this.validUserInfo = candidateFieldNames.entrySet().stream().filter(e -> e.getValue() == SUFFIXES.size())
                                                .map(Map.Entry::getKey)
                                                .collect(Collectors.toSet());
    }

    private @NonNull Optional<String> extractUserField(@NonNull String fieldName, @NonNull String suffix) {
        final var fullSuffix = "user_" + suffix;
        if (fieldName.equals(fullSuffix)) {
            return Optional.of("user");
        }
        if (fieldName.endsWith("_" + fullSuffix)) {
            return Optional.of(fieldName.substring(0, fieldName.length() - fullSuffix.length() - 1));
        }
        return Optional.empty();
    }

    private String computeFieldName(@NonNull String prefix, @NonNull String suffix) {
        if (prefix.equals("user")) {
            return prefix + "_" + suffix;
        }
        return prefix + "_user_" + suffix;
    }

    private void createSetOfFieldNamesUsedInUserInfo() {
        this.fieldNamesUsedInUserInfo = this.validUserInfo.stream()
                                                          .flatMap(p -> SUFFIXES.stream().map(
                                                                  s -> computeFieldName(p, s)))
                                                          .collect(Collectors.toSet());
    }


    private void addUserInfoFields() {
        validUserInfo.forEach(this::addUserInfoField);
    }

    private void addUserInfoField(@NonNull String prefix) {
        final var userInfo = source.putObject(prefix);
        for (String suffix : SUFFIXES) {
            userInfo.put(suffix, source.get(computeFieldName(prefix, suffix)).asText());
        }
    }

    private void removeFieldsInUserInfo() {
        fieldNamesUsedInUserInfo.forEach(source::remove);
    }


}
