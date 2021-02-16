package perobobbot.frontfx.action.list;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.lang.Secret;
import perobobbot.validation.Validation;

import java.util.stream.Stream;

public class SignParameterValidationTest {


    public static @NonNull Stream<SignParameter> validParameters() {
        return Stream.of(
            new SignParameter("Bruce", Secret.of("batman")),
            new SignParameter("Clark", Secret.of("superman")),
            new SignParameter("Thomas", Secret.of("b")),
            new SignParameter("S(D()S", Secret.of("helloAS&*@(#!"))
            );
    }

    public static @NonNull Stream<SignParameter> invalidParameters() {
        return Stream.of(
                new SignParameter("",Secret.of("SSDA")),
                new SignParameter("   ",Secret.of("BBsasd")),
                new SignParameter("Bruce",Secret.of("")),
                new SignParameter("Clark",Secret.of("  ")),
                new SignParameter("Clark",Secret.of("    ")),
                new SignParameter("Clark",Secret.of("      ")),
                new SignParameter("   ",Secret.of("")),
                new SignParameter("   ",Secret.of("   ")),
                new SignParameter("",Secret.of("")),
                new SignParameter("",Secret.of("   "))
        );
    }

    public static @NonNull Stream<SignParameter> invalidLogins() {
        return Stream.of(
                new SignParameter("",Secret.of("SSDA")),
                new SignParameter("   ",Secret.of("BBsasd")),
                new SignParameter("    ",Secret.of("1234567")),
                new SignParameter(" ",Secret.of("password"))
        );
    }
    public static @NonNull Stream<SignParameter> invalidSecrets() {
        return Stream.of(
                new SignParameter("Bruce",Secret.of("    ")),
                new SignParameter("Clark",Secret.of("   ")),
                new SignParameter("Thomas",Secret.of(" ")),
                new SignParameter("Frank",Secret.of(""))
        );
    }


        @ParameterizedTest
    @MethodSource("validParameters")
    public void shouldBeValid(@NonNull SignParameter parameter) {
        final var validation = parameter.validate(Validation.create());
        Assertions.assertTrue(validation.isValid());
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    public void shouldBeInvalid(@NonNull SignParameter parameter) {
        final var validation = parameter.validate(Validation.create());
        Assertions.assertFalse(validation.isValid());
    }

    @ParameterizedTest
    @MethodSource("invalidLogins")
    public void loginShouldBeInvalid(@NonNull SignParameter parameter) {
        final var validationResult = parameter.validate(Validation.create()).getResult();
        Assertions.assertTrue(validationResult.getValidatedFields().contains(SignParameter.LOGIN_FIELD),"Login should be in invalid fields");

    }

    @ParameterizedTest
    @MethodSource("invalidSecrets")
    public void secretShouldBeInvalid(@NonNull SignParameter parameter) {
        final var validationResult = parameter.validate(Validation.create()).getResult();
        Assertions.assertTrue(validationResult.getValidatedFields().contains(SignParameter.SECRET_FIELD),"Secret should be in invalid fields");

    }

}
