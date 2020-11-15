package perobobbot.lang;

public class MathTool {

    public static final double DEGREE_TO_RADIAN_FACTOR = Math.PI/180;

    public static int clamp(int value, int minValue, int maxValue) {
        return Math.min(maxValue, Math.max(minValue,value));
    }

    public static float mod(float value, int base) {
        return (value%base)+(value<0?base:0);
    }

    public static double mod(double value, int base) {
        return (value%base)+(value<0?base:0);
    }


    public static double degreeToRadian(double angleInDegree) {
        return angleInDegree*DEGREE_TO_RADIAN_FACTOR;
    }
}
