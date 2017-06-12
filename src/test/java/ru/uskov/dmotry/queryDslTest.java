package ru.uskov.dmotry;

import com.querydsl.sql.SQLServer2012Templates;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.uskov.dmitry.dao.DeviceDao;
import ru.uskov.dmitry.entity.Device;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dmitry on 11.06.2017.
 */
public class queryDslTest {

    public static void main(String[] args) throws SQLException {
        DeviceDao dao = new DeviceDao();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl("jdbc:sqlserver://imbananko.database.windows.net;databaseName=junk_care;user=dev;password=INN_SQL123");
        dao.setDataSource(dataSource);
        dao.setTemplates(new SQLServer2012Templates());
        dao.init();
        Object obj = dao.getWithUserIds(1);
        System.out.println(obj);
        List<Device> devices = dao.getAllWithUserCount();
        System.out.println(devices);
    }
}