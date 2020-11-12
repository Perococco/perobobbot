package perobobbot.data.domain;

import lombok.NonNull;

import javax.persistence.*;
import java.util.function.Function;

/**
 * @author Perococco
 */
@MappedSuperclass
public abstract class SimplePersistentObject extends PersistentObject<Long> {

    private static final long servialVersionUID = 1L;

    public static final String ID_COLUMN_NAME = "ID";

    public static Function<SimplePersistentObject, Long> ID_GETTER = SimplePersistentObject::getId;

    /**
     * Id of the entity
     */
    @Id
    @Column(name= ID_COLUMN_NAME, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    protected void setId(@NonNull Long id) {
        this.id = id;
    }
}
