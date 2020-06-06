package mastersunny.rooms.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import mastersunny.rooms.entities.CustomerEntity;

@Dao
public interface CustomerDao {

    @Insert
    Long insert(CustomerEntity customerEntity);

    @Query("DELETE FROM customer")
    void deleteAll();

    @Query("Select * from customer where id=:id")
    CustomerEntity getById(Long id);

    @Update
    void update(CustomerEntity customerEntity);

    @Delete
    void delete(CustomerEntity customerEntity);

    @Query("Select * from customer order by id desc limit 1")
    CustomerEntity getFirst();

}
