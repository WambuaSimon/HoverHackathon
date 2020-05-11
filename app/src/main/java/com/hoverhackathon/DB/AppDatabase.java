
package com.hoverhackathon.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.hoverhackathon.Dao.MessageDAO;
import com.hoverhackathon.Dao.TransactionDAO;
import com.hoverhackathon.Message;
import com.hoverhackathon.model.TransactionModel;

@Database(entities = {Message.class, TransactionModel.class}, version = 7,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "message_db";
    static AppDatabase appDatabase;

    public static synchronized AppDatabase getCfctDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public abstract MessageDAO messageDAO();
    public  abstract TransactionDAO transactionDAO();
}

