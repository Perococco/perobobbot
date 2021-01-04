package perobobbot.data.domain;

import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author Perococco
 */
@MappedSuperclass
public abstract class PersistentObjectWithUUID extends SimplePersistentObject {

    private static final long serialVersionUID = 1L;

    @Column(name= "EXTERNAL_ID", nullable = false)
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
