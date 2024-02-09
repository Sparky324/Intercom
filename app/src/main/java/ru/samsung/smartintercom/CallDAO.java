package ru.samsung.smartintercom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface CallDAO {
    @Query("SELECT * FROM call")
    List<Call> getAll();

    @Query("SELECT * FROM call WHERE id IN (:callIds)")
    List<Call> loadAllByIds(int[] callIds);

    @Query("SELECT * FROM call WHERE time LIKE :first AND " +
            "status LIKE :last LIMIT 1")
    Call findByName(String first, String last);

    @Insert
    void insertAll(Call... calls);

    @Delete
    void delete(Call calls);
}
