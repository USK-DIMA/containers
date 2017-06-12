package ru.uskov.dmotry;

import com.querydsl.sql.SQLServer2012Templates;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.uskov.dmitry.dao.UserDao;
import ru.uskov.dmitry.enums.UserRole;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry on 12.06.2017.
 */
public class UserDaoTest {

    public static void main(String[] args) throws SQLException {
        UserDao dao = new UserDao();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://imbananko.database.windows.net;databaseName=junk_care;user=dev;password=INN_SQL123");
        dao.setDataSource(dataSource);
        dao.setTemplates(new SQLServer2012Templates());
        dao.init();
        System.out.println("START");
        dao.deleteRoles(1);
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(UserRole.ROLE_ADMIN);
        roleSet.add(UserRole.ROLE_MANAGER);
        dao.insertUserRoles(1, roleSet);
        System.out.println("end");
    }
}