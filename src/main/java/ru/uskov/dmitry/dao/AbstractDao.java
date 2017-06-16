package ru.uskov.dmitry.dao;

import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by Dmitry on 11.03.2017.
 */
abstract public class AbstractDao {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SQLTemplates templates;

    protected SQLQueryFactory queryFactory;

    @PostConstruct
    public void init() throws SQLException {
        queryFactory = new SQLQueryFactory(new Configuration(templates), dataSource);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setTemplates(SQLTemplates templates) {
        this.templates = templates;
    }


}
