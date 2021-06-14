package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeNodeModifier {

    private static final Set<String> SUFFIXES = Set.of("id", "login", "name");

    public static void modify(@NonNull JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            new TreeNodeModifier((ObjectNode) jsonNode).modify();
        }
    }

    private final @NonNull ObjectNode source;

    private Set<String> fieldNames;

    private Map<String, Long> candidateFieldNames;
    private Set<String> validUserInfo;
    private Set<String> fieldNamesUsedInUserInfo;

    private void modify() {
        this.gatherFieldNames();
        this.countUserInfoCandidats();
        this.filterValidUserInfo();
        this.createSetOfFieldNamesUsedInUserInfo();
        this.addUserInfoFields();
        this.removeFieldsInUserInfo();
        this.modifyObjectFields();
        this.replaceBooleanFields();
    }

    private void gatherFieldNames() {
        this.fieldNames = new HashSet<>();
        final var itr = source.fieldNames();
        while (itr.hasNext()) {
            fieldNames.add(itr.next());
        }
    }

    private void countUserInfoCandidats() {
        candidateFieldNames = new HashMap<>();
        this.candidateFieldNames = fieldNames.stream()
                                             .map(this::getUserInfoPrefix)
                                             .flatMap(Optional::stream)
                                             .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    }

    private @NonNull Optional<String> getUserInfoPrefix(@NonNull String fieldName) {
        return SUFFIXES.stream().map(s -> extractUserField(fieldName, s))
                       .flatMap(Optional::stream)
                       .findAny();
    }

    private void filterValidUserInfo() {
        this.validUserInfo = candidateFieldNames.entrySet()
                                                .stream()
                                                .filter(e -> e.getValue() == SUFFIXES.size())
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
        final Map<String,JsonNode> nodes = new HashMap<>();
        boolean allNull = true;
        for (String suffix : SUFFIXES) {
            final var node = source.get(computeFieldName(prefix,suffix));
            nodes.put(suffix,node);
            allNull &= node.isNull();
        }
        if (allNull) {
            source.putNull(prefix);
        } else {
            final var userInfo = source.putObject(prefix);
            nodes.forEach(userInfo::replace);
        }
    }

    private void removeFieldsInUserInfo() {
        fieldNamesUsedInUserInfo.forEach(source::remove);
    }

    private void replaceBooleanFields() {
        for (String fieldName : fieldNames) {
            if (fieldName.startsWith("is_")) {
                if (source.get(fieldName).isBoolean()) {
                    final var node = source.remove(fieldName);
                    source.replace(fieldName.substring("is_".length()), node);
                }
            }
        }
    }

    private void modifyObjectFields() {
        for (String fieldName : fieldNames) {
            if (validUserInfo.contains(fieldName)) {
                continue;
            }
            final var node = source.get(fieldName);
            if (node == null) {
                continue;
            }
            if (node.isArray()) {
                final var arrayNode = (ArrayNode)node;
                arrayNode.elements().forEachRemaining(TreeNodeModifier::modify);
            }
            else if (node.isObject()) {
                TreeNodeModifier.modify(node);
            }
        }
    }


}
