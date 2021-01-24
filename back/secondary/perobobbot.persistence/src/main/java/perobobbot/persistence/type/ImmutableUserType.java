package perobobbot.persistence.type;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.util.Objects;

public abstract class ImmutableUserType implements UserType {
    public ImmutableUserType() {
    }

    public final boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    public final int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    public final Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public final boolean isMutable() {
        return false;
    }

    public final Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    public final Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public final Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
