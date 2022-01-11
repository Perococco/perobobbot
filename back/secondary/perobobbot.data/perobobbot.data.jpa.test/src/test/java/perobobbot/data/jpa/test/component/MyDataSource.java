package perobobbot.data.jpa.test.component;

import lombok.NonNull;
import lombok.experimental.Delegate;
import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Referenceable;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.io.Serializable;

/**
 * Wrap the Datasource from H2 since it does not declare a setUserName() method
 * but a setUser() one. A setUsername() method is necessary to set the user name
 * from the application.properties
 */
public class MyDataSource implements XADataSource, DataSource, ConnectionPoolDataSource, Serializable, Referenceable{

    @Delegate
    private final JdbcDataSource jdbcDataSource = new JdbcDataSource();

    public void setUsername(@NonNull String username) {
        jdbcDataSource.setUser(username);
    }

}
