package perobobbot.common.lang;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ListToolTest {

    private ImmutableList<Integer> source;

    @BeforeEach
    public void setUp() {
        source = ImmutableList.of(0,2,4,6);
    }

    @Test
    public void shouldHaveMinus1AtTheBeginning() {
        final ImmutableList<Integer> actual = ListTool.addInOrderedList(source,-1);
        Assertions.assertEquals(List.of(-1,0,2,4,6),actual);
    }
    @Test
    public void shouldHave8AtTheEnd() {
        final ImmutableList<Integer> actual = ListTool.addInOrderedList(source,8);
        Assertions.assertEquals(List.of(0,2,4,6,8),actual);
    }
    @Test
    public void shouldHave5InMiddle() {
        final ImmutableList<Integer> actual = ListTool.addInOrderedList(source,5);
        Assertions.assertEquals(List.of(0,2,4,5,6),actual);
    }

    @Test
    public void shouldHave7AtTheEnd() {
        final ImmutableList<Integer> actual = ListTool.addInOrderedList(source,7);
        Assertions.assertEquals(List.of(0,2,4,6,7),actual);
    }

    @Test
    public void shouldHave0AtBeginning() {
        final ImmutableList<Integer> actual = ListTool.addInOrderedList(source,0);
        Assertions.assertEquals(List.of(0,0,2,4,6),actual);
    }
}
