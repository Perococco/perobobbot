package perobobbot.proxy;

import lombok.NonNull;

import java.lang.annotation.Annotation;

/**
 * @author perococco
 */
public class AnnotationInterceptorFilter implements InterceptorFilter {

    private final Class<? extends Annotation> annotationClass;

    public AnnotationInterceptorFilter(@NonNull Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public AnnotationInterceptorFilter() {
        this(NotIntercepted.class);
    }

    @Override
    public @NonNull InterceptorFilter cached() {
        return new CachedInterceptorFilter(this);
    }

    @Override
    public boolean filtered(@NonNull MethodCallParameter methodCallParameter) {
        Annotation annotation = methodCallParameter.getMethod().getAnnotation(annotationClass);
        return annotation != null;
    }
}
