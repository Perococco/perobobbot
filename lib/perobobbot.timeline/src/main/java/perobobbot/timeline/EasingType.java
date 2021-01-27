package perobobbot.timeline;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.perobobbot.timeline.easing.*;

@RequiredArgsConstructor
public enum EasingType {
    LINEAR(new Linear()),

    EASE_IN_SINE(new EaseSineIn()),
    EASE_OUT_SINE(new EaseSineIn().reverse()),
    EASE_IN_OUT_SINE(new EaseSineIn().symmetrize()),

    EASE_IN_QUAD(new EaseQuadIn()),
    EASE_OUT_QUAD(new EaseQuadIn().reverse()),
    EASE_IN_OUT_QUAD(new EaseQuadIn().symmetrize()),

    EASE_IN_CUBIC(new EaseCubicIn()),
    EASE_OUT_CUBIC(new EaseCubicIn().reverse()),
    EASE_IN_OUT_CUBIC(new EaseCubicIn().symmetrize()),

    EASE_IN_QUART(new EaseQuartIn()),
    EASE_OUT_QUART(new EaseQuartIn().reverse()),
    EASE_IN_OUT_QUART(new EaseQuartIn().symmetrize()),

    EASE_IN_QUINT(new EaseQuintIn()),
    EASE_OUT_QUINT(new EaseQuintIn().reverse()),
    EASE_IN_OUT_QUINT(new EaseQuintIn().symmetrize()),

    EASE_IN_EXPO(new EaseExpoIn()),
    EASE_OUT_EXPO(new EaseExpoIn().reverse()),
    EASE_IN_OUT_EXPO(new EaseExpoIn().symmetrize()),

    EASE_IN_CIRC(new EaseCircIn()),
    EASE_OUT_CIRC(new EaseCircIn().reverse()),
    EASE_IN_OUT_CIRC(new EaseCircIn().symmetrize()),

    EASE_IN_BACK(new EaseBackIn()),
    EASE_OUT_BACK(new EaseBackIn().reverse()),
    EASE_IN_OUT_BACK(new EaseBackIn().symmetrize()),

    EASE_IN_ELASTIC(new EaseElasticIn()),
    EASE_OUT_ELASTIC(new EaseElasticIn().reverse()),
    EASE_IN_OUT_ELASTIC(new EaseElasticIn().symmetrize()),

    EASE_IN_BOUNCE(new EaseExpoIn()),
    EASE_OUT_BOUNCE(new EaseExpoIn().reverse()),
    EASE_IN_OUT_BOUNCE(new EaseExpoIn().symmetrize()),
    ;

    @Getter
    private final @NonNull EasingOperator easingOperator;
}
