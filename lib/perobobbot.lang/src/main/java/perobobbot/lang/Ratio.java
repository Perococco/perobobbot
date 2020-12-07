package perobobbot.lang;

import com.google.common.math.IntMath;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Ratio extends Number {

    public static Ratio create(int numerator, int denominator) {
        if (denominator == 0) {
                return new Ratio(Integer.compare(numerator, 0), 0);
        }
        if (denominator < 0) {
            return create(-numerator,-denominator);
        }
        if (numerator == 0) {
            return ZERO;
        }
        int gcd = IntMath.gcd(Math.abs(numerator),denominator);
        return new Ratio(numerator/gcd,denominator/gcd);
    }

    public static final Ratio ZERO = new Ratio(0,1);

    @Getter
    private final int numerator;

    @Getter
    private final int denominator;

    public boolean isInteger() {
        return denominator == 1;
    }

    public int ceil() {
        if (denominator == 1) {
            return numerator;
        }
        if (numerator<0) {
            return intValue();
        } else {
            return intValue()+1;
        }
    }

    @NonNull
    public Ratio getFracPart() {
        final int intPart = intValue();
        return create((numerator - denominator*intPart),denominator);
    }

    @NonNull
    public Ratio invert() {
        return create(denominator,numerator);
    }

    @NonNull
    public Ratio negate() {
        return create(-numerator,denominator);
    }

    @NonNull
    public Ratio multiply(int value) {
        if (value==0) {
            return ZERO;
        } else if (value<0) {
            return multiply(-value).negate();
        }
        final int gcd = IntMath.gcd(value,denominator);
        return Ratio.create(numerator*value/gcd,denominator/gcd);
    }

    @Override
    public int intValue() {
        return numerator/denominator;
    }

    @Override
    public long longValue() {
        return intValue();
    }

    @Override
    public float floatValue() {
        return ((float)numerator)/denominator;
    }

    @Override
    public double doubleValue() {
        return ((double)numerator)/denominator;
    }
}
