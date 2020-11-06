package perobobbot.program.core;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(of = {"programName"})
public class ProgramInfo implements Comparable<ProgramInfo> {

    @NonNull String programName;

    boolean running;

    @Override
    public int compareTo(ProgramInfo o) {
        return this.programName.compareTo(o.programName);
    }
}
