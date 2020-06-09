package mastersunny.rooms.repositories;

import android.app.Application;
import mastersunny.rooms.config.BookingDatabase;
import mastersunny.rooms.dao.CustomerDao;
import mastersunny.rooms.entities.CustomerEntity;

public class CustomerRepository {

    private CustomerDao customerDao;

    public CustomerRepository(Application application) {
        BookingDatabase db = BookingDatabase.getDatabase(application);
        customerDao = db.customerDao();
    }

    public void insert(CustomerEntity customerEntity) {
        BookingDatabase.databaseWriteExecutor.execute(() -> {
            customerDao.insert(customerEntity);
        });
    }

    public void deleteAll(){
        BookingDatabase.databaseWriteExecutor.execute(() -> {
            customerDao.deleteAll();
        });
    }

    public void delete(final long id) {
        BookingDatabase.databaseWriteExecutor.execute(() -> {
            CustomerEntity customerEntity = customerDao.getById(id);
            if (customerEntity != null) {
                customerDao.delete(customerEntity);
            }
        });
    }

    public CustomerEntity getById(Long customerId) {
        return customerDao.getById(customerId);
    }

    CustomerEntity customerEntity=null;

    public CustomerEntity getFirst(){
        return null;
    }


}
