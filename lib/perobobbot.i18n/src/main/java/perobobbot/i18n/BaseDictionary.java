package perobobbot.i18n;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perococco.perobobbot.i18n.LocalizedStringFromResource;
import perococco.perobobbot.i18n.PeroResourceBundle;
import perococco.perobobbot.i18n.PrefixedDictionary;
import perococco.perobobbot.i18n.ResourceReference;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.spi.ResourceBundleProvider;

/**
 * @author perococco
 */
public class BaseDictionary implements Dictionary {

    @NonNull
    private final ResourceBundleProvider resourceBundleProvider;

    private final String baseResourceName;

    public BaseDictionary(@NonNull ResourceBundleProvider resourceBundleProvider, @NonNull String baseResourceName) {
        this.resourceBundleProvider = resourceBundleProvider;
        this.baseResourceName = baseResourceName;
    }

    public BaseDictionary(@NonNull BaseResourceBundleProvider resourceBundleProvider) {
        this.resourceBundleProvider = resourceBundleProvider;
        this.baseResourceName = getClass().getName();
    }

    @NonNull
    public LocalizedString localizedString(@NonNull String i18nKey) {
        final ResourceReference resourceReference = new ResourceReference(baseResourceName, i18nKey);
        return new LocalizedStringFromResource(this::getResourceBundle, resourceReference, ImmutableList.of());
    }

    @NonNull
    public LocalizedString localizedString(@NonNull String i18nKey, @NonNull Object... parameters) {
        final ResourceReference resourceReference = new ResourceReference(baseResourceName,i18nKey);
        return new LocalizedStringFromResource(this::getResourceBundle, resourceReference, ImmutableList.copyOf(parameters));
    }

    @NonNull
    public Dictionary withPrefix(@NonNull String i18nPrefix) {
        return new PrefixedDictionary(this, i18nPrefix);
    }

    @Override
    public @NonNull ResourceBundle getResourceBundle(@NonNull Locale locale) {
        return PeroResourceBundle.create(resourceBundleProvider, baseResourceName, locale, Locale.ENGLISH);
    }
}
