package com.hoverhackathon.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hoverhackathon.Message;
import com.hoverhackathon.model.TransactionModel;

import org.w3c.dom.ls.LSInput;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Query("SELECT * FROM TransactionModel")
    List<TransactionModel> getTransactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(TransactionModel transactionModel);

}
