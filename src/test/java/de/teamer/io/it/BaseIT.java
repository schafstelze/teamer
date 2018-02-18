package de.teamer.io.it;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.io.IOException;
import java.net.ServerSocket;

import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_17;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = BaseIT.Config.class)
public abstract class BaseIT {

    @LocalServerPort
    protected int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @TestConfiguration
    public static class Config {

        public static final String DB_USERNAME = "test";
        public static final String DB_PASSWORD = "123";
        public static final String DB_SCHEMA_NAME = "teamer";

        @Bean
        public DataSource dataSource() {
            final int port = findFreePort();

            startEmbeddedMysql(port);

            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:" + port + "/" + DB_SCHEMA_NAME)
                    .username(DB_USERNAME)
                    .password(DB_PASSWORD)
                    .build();

        }

        private void startEmbeddedMysql(final int port) {
            MysqldConfig config = aMysqldConfig(v5_7_17)
                    .withPort(port)
                    .withUser(DB_USERNAME, DB_PASSWORD)
                    .build();

            EmbeddedMysql.anEmbeddedMysql(config).addSchema(DB_SCHEMA_NAME).start();
        }

        private int findFreePort() {
            try {
                ServerSocket serverSocket = new ServerSocket(0);
                int port = serverSocket.getLocalPort();
                serverSocket.close();

                return port;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
