package perococco.perobobbot.rest.client.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.security.com.RoleKind;
import perobobbot.security.com.SimpleUser;

import java.util.Locale;

public class TestUserListSerialization {

    private ImmutableList<SimpleUser> inputList;

    private Object deserializedList;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        final var mapper = RestObjectMapper.create();

        final var type = mapper.constructType(ImmutableList.class).withContentType(mapper.constructType(SimpleUser.class));


        inputList = ImmutableList.of(
                new SimpleUser("bwayne", Locale.ENGLISH, false, ImmutableSet.of(RoleKind.ADMIN)),
                new SimpleUser("alupin", Locale.FRANCE, false, ImmutableSet.of(RoleKind.USER))
        );

        final String serialized = mapper.writeValueAsString(inputList);
        this.deserializedList = mapper.readValue(serialized, type);

    }

    @Test
    public void deserializedListShouldNotBeNull() {
        Assertions.assertNotNull(deserializedList);
    }

    @Test
    public void deserializedListShouldBeAnImmutableList() {
        Assertions.assertTrue(deserializedList instanceof ImmutableList);
    }

    @Test
    public void deserializedListShouldHave2Items() {
        Assertions.assertEquals(2,((ImmutableList<?>)deserializedList).size());
    }

    @Test
    public void deserializeListShouldContainSimpleUsers() {
        final var list = ((ImmutableList<?>)deserializedList);
        Assertions.assertEquals(SimpleUser.class, list.get(0).getClass());
        Assertions.assertEquals(SimpleUser.class, list.get(1).getClass());
    }
}
