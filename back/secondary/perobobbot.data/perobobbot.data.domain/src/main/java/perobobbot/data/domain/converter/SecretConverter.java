package perobobbot.data.domain.converter;

import perobobbot.lang.Secret;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class SecretConverter implements AttributeConverter<Secret,String> {

    @Override
    public String convertToDatabaseColumn(Secret attribute) {
        return attribute == null ? null:attribute.getValue();
    }

    @Override
    public Secret convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new Secret(dbData);
    }
}
