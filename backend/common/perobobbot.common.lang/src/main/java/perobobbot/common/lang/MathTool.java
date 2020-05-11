package perobobbot.common.lang;

public class MathTool {

    public static int clamp(int value, int minValue, int maxValue) {
        return Math.min(maxValue, Math.max(minValue,value));
    }
}
