package perobobbot.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * @author perococco
 */
@MappedSuperclass
public abstract class PersistentObject<I> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String VERSION_COLUMN_NAME = "VERSION";

    @Transient
    protected  transient final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Object version (used to handle concurrent access to the table)
     */
    @Column(name=VERSION_COLUMN_NAME, nullable = false)
    @Version
    private int version;

    /**
     * @return the id of this entity
     */
    public abstract I getId();

    protected abstract void setId(I id);

    /**
     * @return the version of this entity
     */
    public int getVersion() {
        return this.version;
    }

    /**
     * @param version the new version of this entity
     */
    protected void setVersion(int version) {
        this.version = version;
    }

}
