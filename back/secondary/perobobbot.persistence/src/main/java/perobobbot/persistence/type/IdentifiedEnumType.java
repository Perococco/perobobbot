package perobobbot.persistence.type;

import lombok.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.IdentifiedEnumTools;

import javax.persistence.Id;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class IdentifiedEnumType  extends ImmutableUserType implements DynamicParameterizedType {


    private final int SQL_TYPE = Types.VARCHAR;

    private Class<? extends IdentifiedEnum> enumType;

    @Override
    public int[] sqlTypes() {
        return new int[]{SQL_TYPE};
    }
    @Override
    public Class returnedClass() {
        return enumType;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);
        if (reader == null) {
            throw new IllegalStateException("Cannot find type of enumerate : "+parameters.getProperty(PROPERTY));
        }

        final Class<?> type = reader.getReturnedClass();

        if (!type.isEnum() || !IdentifiedEnum.class.isAssignableFrom(type)) {
            throw new IllegalStateException("IdentifiedEnumType only applies to enum type implementing IdentifiedEnumType : '"+type+"' does not");
        }
        enumType = ((Class<?>)reader.getReturnedClass()).asSubclass(IdentifiedEnum.class);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session,
            Object owner) throws HibernateException, SQLException {
        final String name = names[0];
        final String value = name == null ? null: rs.getString(name);
        return value == null ? null:findEnum(value);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
            SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value instanceof IdentifiedEnum) {
            st.setString(index,((IdentifiedEnum) value).getIdentification());
        } else {
            st.setNull(index, SQL_TYPE);
        }
    }



    public Object findEnum(@NonNull String id) {
        return IdentifiedEnumTools.getEnum(id,enumType);
    }
}
