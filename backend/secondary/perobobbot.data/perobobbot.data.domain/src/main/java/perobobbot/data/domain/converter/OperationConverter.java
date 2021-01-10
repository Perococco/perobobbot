package perobobbot.data.domain.converter;

import perobobbot.security.com.Operation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OperationConverter implements AttributeConverter<Operation,String> {

    @Override
    public String convertToDatabaseColumn(Operation attribute) {
        return attribute.name();
    }

    @Override
    public Operation convertToEntityAttribute(String dbData) {
        return Operation.valueOf(dbData);
    }
}
