package mobiledev.unb.ca.roompersistencelab.repository;

import android.app.Application;

import mobiledev.unb.ca.roompersistencelab.dao.ItemDao;
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase;
import mobiledev.unb.ca.roompersistencelab.entity.Item;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ItemRepository {
    private ItemDao itemDao;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        itemDao = db.itemDao();
    }

    //  See the example project file at
    //  https://github.com/hpowell20/cs2063-winter-2021-examples/blob/master/Lecture7/RoomPersistenceLibraryDemo/app/src/main/java/mobiledev/unb/ca/roompersistencetest/repository/ItemRepository.java
    //  to see examples of how to work with the Executor Service

    // TODO Add query specific methods
    //  HINTS
    //   The insert operation needs to make use of the Runnable interface
    //   The search operation needs to make use of the Callable interface
    public void insertItem(final Item item) {
        AppDatabase.databaseWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                itemDao.insert(item);
            }
        });
    }

    public void insertItem(final String name, final int num) {
        AppDatabase.databaseWriterExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Item item = new Item();
                item.setName(name);
                item.setNum(num);
                insertItem(item);
            }
        });
    }

    public List<Item> searchItem(final String name) {
        Future<List<Item>> future = AppDatabase.databaseWriterExecutor.submit(new Callable<List<Item>>() {
            public List<Item> call() {
                return itemDao.search(name);
            }
        });
        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
