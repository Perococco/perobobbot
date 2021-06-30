package perobobbot.tsgen;

import com.blueveery.springrest2ts.tsmodel.TSEnumConstant;

import java.io.BufferedWriter;
import java.io.IOException;

public class PeroEnumConstant extends TSEnumConstant {

    private final String value;

    private final boolean ordinal;

    public PeroEnumConstant(String name, String value, boolean ordinal) {
        super(name, ordinal);
        this.value = value;
        this.ordinal = ordinal;
    }

    @Override
    public void write(BufferedWriter writer) throws IOException {
        writer.write(this.getName());
        if (!this.ordinal) {
            writer.write(" = '" + this.value + "'");
        }
    }
}
