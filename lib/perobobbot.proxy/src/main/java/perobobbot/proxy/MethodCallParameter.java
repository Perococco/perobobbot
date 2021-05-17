package perobobbot.proxy;


import perobobbot.proxy._private.ObjectStringifier;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * @author perococco
 */
public class MethodCallParameter {

    private static final ObjectStringifier OBJECT_STRINGNIFIER = new ObjectStringifier(100);

    public static Function<Object[], String[]> STRINGNIFIER = OBJECT_STRINGNIFIER::toStrings;

    private final Object instance;

    private final Method method;

    private final Object[] args;

    private final UnaryOperator<String> computeDetailedName = s -> computeMethodName(s, true);
    private final UnaryOperator<String> computeSimpleName = s -> computeMethodName(s, false);

    public MethodCallParameter(Object instance, Method method, Object[] args) {
        this.args = args;
        this.instance = instance;
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getInstance() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }

    public Object invoke() throws Throwable {
        try {
            return this.method.invoke(this.instance, this.args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private AtomicReference<String> simpleName = new AtomicReference<>();
    private AtomicReference<String> detailName = new AtomicReference<>();

    public String getMethodName() {
        return getMethodName(false);
    }

    public String getMethodNameWithArgs() {
        return getMethodName(true);
    }

    public String getMethodName(boolean detailed) {
        if (detailed) {
            return detailName.updateAndGet(computeDetailedName);
        } else {
            return simpleName.updateAndGet(computeSimpleName);
        }
    }

    private String computeMethodName(String currentValue, boolean detailed) {
        if (currentValue != null) {
            return currentValue;
        }
        final String arguments = getArgumentsString(detailed);

        return String.valueOf(this.instance) +
                "." +
                this.method.getName() +
                "(" +
                arguments +
                ")";
    }

    private String getSimpleName(String currentValue) {
        if (currentValue != null) {
            return currentValue;
        }
        final String arguments = getArgumentsString(false);

        return String.valueOf(this.instance) +
                "." +
                this.method.getName() +
                "(" +
                arguments +
                ")";
    }

    private String getArgumentsString(boolean detailed) {
        if (!detailed) {
            return "...";
        } else if (args == null || args.length == 0) {
            return "";
        } else {
            return Arrays.stream(STRINGNIFIER.apply(args)).collect(Collectors.joining(", "));
        }
    }

}
