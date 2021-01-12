package perobobbot.data.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Locale;

@Converter
public class LocaleConverter implements AttributeConverter<Locale,String> {

    @Override
    public String convertToDatabaseColumn(Locale attribute) {
        return attribute.toLanguageTag();
    }

    @Override
    public Locale convertToEntityAttribute(String dbData) {
        return Locale.forLanguageTag(dbData);
    }
}



