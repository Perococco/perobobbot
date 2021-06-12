package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Value2;

import java.util.OptionalInt;
import java.util.Random;

public class MathTool {

    private static final Random RANDOM = new Random();


    public static <T> @NonNull Value2<T,T> shuffle(@NonNull T v1, @NonNull T v2) {
        if (RANDOM.nextInt(2) == 0) {
            return Value2.of(v1,v2);
        } else {
            return Value2.of(v2,v1);
        }
    }


    public static int clamp(int value, int minValue, int maxValue) {
        return Math.min(maxValue, Math.max(minValue,value));
    }

    public static float mod(float value, int base) {
        return (value%base)+(value<0?base:0);
    }

    public static double mod(double value, int base) {
        return (value%base)+(value<0?base:0);
    }


    public static int roundedToInt(double value) {
        return (int)Math.round(value);
    }
}
