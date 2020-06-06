package mastersunny.rooms.config;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import mastersunny.rooms.dao.CustomerDao;
import mastersunny.rooms.entities.CustomerEntity;

@Database(entities = {CustomerEntity.class}, version = 1, exportSchema = false)
public abstract class BookingDatabase extends RoomDatabase {

    public abstract CustomerDao customerDao();

    private static volatile BookingDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BookingDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookingDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookingDatabase.class, "booking_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
