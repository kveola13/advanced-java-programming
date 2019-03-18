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

@DatabaseTable(tableName = "land")
public class Country {
    private static final String NAME_FIELD_NAME = "admin";
    private static final String PASSWORD_FIELD_NAME = "admin";
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField
    private String navn;
    private String databaseUrl = "jdbc:mysql://localhost:3306/world?useSSL=false";

    public Country() {

    }
    public String getName(){
        return navn;
    }
    public void setName(String name){
        this.navn = name;
    }
    public void connectToServerAndInsertData() throws SQLException {
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, NAME_FIELD_NAME, PASSWORD_FIELD_NAME);
        Dao<Country, String> accountDao = DaoManager.createDao(connectionSource, Country.class);
        TableUtils.createTableIfNotExists(connectionSource, Country.class);
        Country country = new Country();
        country.setName("New Norway");
        if (accountDao.createIfNotExists(country) == null) {
            throw new SQLException("Can not create country");
        }
            List<Country> accounts = accountDao.queryForEq("navn", "New Norway");
            System.out.println("Country: " + accounts.get(0).getName());
            QueryBuilder<Country, String> queryBuilder = accountDao.queryBuilder();
            queryBuilder.where().eq("id", "0");
            PreparedQuery<Country> preparedQuery = queryBuilder.prepare();
            List<Country> accountList = accountDao.query(preparedQuery);
    }
}