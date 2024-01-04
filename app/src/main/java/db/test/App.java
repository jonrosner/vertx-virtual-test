package db.test;

import java.util.UUID;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.ThreadingModel;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

public class App {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new MockDataCreator(),
                new DeploymentOptions()
                        .setThreadingModel(ThreadingModel.VIRTUAL_THREAD));

        while (true) {
        }
    }

    public static class MockDataCreator extends AbstractVerticle {

        @Override
        public void start() {
            try {
                PgConnectOptions connectOptions = new PgConnectOptions()
                        .setPort(5432)
                        .setHost("127.0.0.1")
                        .setDatabase("db")
                        .setUser("user")
                        .setPassword("password");

                PoolOptions poolOptions = new PoolOptions()
                        .setMaxSize(5);

                SqlClient client = Pool.pool(vertx, connectOptions, poolOptions);

                for (int i = 0; i < 100000; i++) {
                    String id = UUID.randomUUID().toString();

                    String query = String.format(
                            "INSERT INTO users (id, name) VALUES (%s, %s)",
                            id,
                            "user" + i);

                    Future.await(client.query(query).execute());

                    if (i % 100 == 0)
                        System.out.println("created user" + i);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
