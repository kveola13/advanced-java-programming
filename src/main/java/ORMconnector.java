import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class ORMconnector {
    @DatabaseTable(tableName = "Country")
    public static class Country {

        private static String PASSWORD_FIELD_NAME = "admin";
        private static String COUNTRY_FIELD_NAME = "Norway";
        @DatabaseField(generatedId = true)
        String databaseURL = "jdbc:mysql://localhost:3306/world";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseURL, "admin", "admin");
        Dao<Country, String> worldStringDao = DaoManager.createDao(connectionSource, Country.class);
        private String country;

        public Country() throws SQLException {

        }

        public void setCountryFieldName(String countryFieldName) {
            COUNTRY_FIELD_NAME = countryFieldName;
        }

        public void setPASSWORD_FIELD_NAME(String PASSWORD_FIELD_NAME) {
            this.PASSWORD_FIELD_NAME = PASSWORD_FIELD_NAME;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void worldDBQuery() throws SQLException {
            TableUtils.createTableIfNotExists(connectionSource, Country.class);
        }

        public void createAccount() throws SQLException {
            Country country = new Country();
            country.setCountry("Norway");
        }

        public void getDataFromWorld() throws SQLException {
            Dao<Country, String> worldStringDao = DaoManager.createDao(connectionSource, Country.class);
            List<Country> countryList = worldStringDao.queryForEq(Country.COUNTRY_FIELD_NAME, "Norway");
            System.out.println("Country:" + countryList.get(0).getCountry());
        }

        public void buildQuery() throws SQLException {
            QueryBuilder<Country, String> queryBuilder = worldStringDao.queryBuilder();
            queryBuilder.where().eq(Country.PASSWORD_FIELD_NAME, "admin");
            PreparedQuery<Country> preparedQuery = queryBuilder.prepare();
            List<Country> countryList = worldStringDao.query(preparedQuery);
        }
    }
}
