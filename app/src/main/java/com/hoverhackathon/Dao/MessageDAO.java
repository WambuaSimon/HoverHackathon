package com.hoverhackathon.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hoverhackathon.Message;

import java.util.List;

@Dao
public interface MessageDAO {

    @Query("SELECT * FROM Message WHERE status =0")
    List<Message> getMessages();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(Message message);

    @Query("UPDATE Message SET status = 1 WHERE messageNumber =:address")
    void updateMessage(String address);
}
