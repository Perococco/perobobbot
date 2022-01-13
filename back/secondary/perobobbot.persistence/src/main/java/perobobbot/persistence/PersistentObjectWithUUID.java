package perobobbot.persistence;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author perococco
 */
@MappedSuperclass
@EqualsAndHashCode(of = {"uuid"},callSuper = false)
public abstract class PersistentObjectWithUUID extends SimplePersistentObject {

    private static final long serialVersionUID = 1L;

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
