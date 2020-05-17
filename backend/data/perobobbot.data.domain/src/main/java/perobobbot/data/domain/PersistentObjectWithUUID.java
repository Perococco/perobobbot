package perobobbot.data.domain;

import lombok.NonNull;

import javax.persistence.*;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author Perococco
 */
@MappedSuperclass
public abstract class PersistentObjectWithUUID extends SimplePersistentObject {

    private static final long servialVersionUID = 1L;

    @Column(name= "EXTERNAL_ID", nullable = false, unique = true)
    protected UUID uuid;

    @NonNull
    public UUID getUuid() {
        return uuid;
    }

    public PersistentObjectWithUUID() {
    }

    public PersistentObjectWithUUID(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    protected void setUuid(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

}
