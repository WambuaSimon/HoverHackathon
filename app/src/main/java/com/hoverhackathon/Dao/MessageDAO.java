package com.hoverhackathon.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hoverhackathon.Message;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MessageDAO {

    @Query("SELECT * FROM Message")
    List<Message> getMessages();

    @Insert
    void insertMessage(Message message);

    @Update
    void updateMessage(Message message);
}
