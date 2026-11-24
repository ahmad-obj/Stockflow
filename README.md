# StockFlow

Warehouse & Supplier MIS for the FAST-NUCES Software Design and Analysis course. Stage 1 establishes the JavaFX build and a separate Oracle connectivity check.

## Build and launch

Install a JDK 21 and set `JAVA_HOME`. Maven Wrapper downloads Maven 3.9.12 and resolves the JavaFX and JDBC dependencies on the first run.

| | Linux / macOS | Windows PowerShell |
|---|---|---|
| Build | `./mvnw clean verify` | `.\mvnw.cmd clean verify` |
| Open the setup window | `./mvnw javafx:run` | `.\mvnw.cmd javafx:run` |

In Eclipse, use **File → Import → Maven → Existing Maven Projects**, select this directory and configure JDK 21 under **Installed JREs**. Run the JavaFX Maven goal from the terminal or an Eclipse Maven Run Configuration.

## Oracle Database Free

Use Docker with Compose for a local Oracle Database Free instance or an existing Oracle service with a dedicated schema account.

1. Copy `.env.example` to `.env`. Set distinct local admin and app passwords.
2. Run `docker compose up -d --wait --wait-timeout 240`. The container creates the `stockflow` account in `FREEPDB1` on first startup. Its named volume keeps the data between runs. Use `docker compose stop` to stop the service.
3. Set these environment variables in the terminal before running the JDBC check. The `.env` file is read by Compose. Enter the same app password in your terminal or IDE environment.

| Variable | Local Oracle value |
|---|---|
| `STOCKFLOW_DB_URL` | `jdbc:oracle:thin:@//127.0.0.1:1521/FREEPDB1` |
| `STOCKFLOW_DB_USER` | `stockflow` |
| `STOCKFLOW_DB_PASSWORD` | The app password in `.env` |

Run `./mvnw -Dstockflow.oracle.smoke=true -Dtest=OracleConnectionTest test` on Linux or macOS, or `.\mvnw.cmd "-Dstockflow.oracle.smoke=true" "-Dtest=OracleConnectionTest" test` on Windows. The test confirms the database is Oracle and queries `DUAL`. The standard build skips this test unless enabled. If you change the host port in `.env`, use the same port in the JDBC URL.

For an interactive SQL check, run `docker compose exec oracle sqlplus -L stockflow@//localhost:1521/FREEPDB1` and enter the app password when prompted.
