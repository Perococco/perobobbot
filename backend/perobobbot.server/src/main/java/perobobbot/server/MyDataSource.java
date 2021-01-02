package perobobbot.server;

import lombok.NonNull;
import lombok.experimental.Delegate;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Referenceable;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.io.Serializable;

public class MyDataSource implements XADataSource, DataSource, ConnectionPoolDataSource, Serializable, Referenceable{

    @Delegate
    private final JdbcDataSource jdbcDataSource = new JdbcDataSource();

    public void setUsername(@NonNull String username) {
        jdbcDataSource.setUser(username);
    }

}
