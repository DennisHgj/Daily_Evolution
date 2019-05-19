package com.example.daliyevolution.util;

import android.util.Log;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;

import java.io.File;

/***
 * Db_config
 * Database config
 * @author Guanjie Huang
 * @ID u6532079
 */


public class Db_config {

    private static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig() {
        if (daoConfig == null) {
            DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                    //set database name，The default is xutils.db
                    .setDbName("Myapp.db")
                    //Set the database path, which is stored in the app's private directory by default
                    .setDbDir(new File("/mnt/sdcard/"))
                    //set version of database
                    .setDbVersion(1)
                    //set database open listener
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            //Open the database to support multi-threaded operation, improve performance
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    })
                    //Set the listener for database updates
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        }
                    })
                    //Set listener for table create
                    .setTableCreateListener(new DbManager.TableCreateListener() {
                        @Override
                        public void onTableCreated(DbManager db, TableEntity<?> table) {
                            Log.i("JAVA", "onTableCreated：" + table.getName());
                        }
                    })
                    //Sets whether transactions are allowed, default to true
                    .setAllowTransaction(true);
        }
        return daoConfig;
    }
}
