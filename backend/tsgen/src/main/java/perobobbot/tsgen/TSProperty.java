package perobobbot.tsgen;

public class TSProperty {

    private final String name;
    private final String value;

    public TSProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name+": "+value;
    }
}
