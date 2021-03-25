package mobiledev.unb.ca.roompersistencelab.ui;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import mobiledev.unb.ca.roompersistencelab.repository.ItemRepository;
import mobiledev.unb.ca.roompersistencelab.entity.Item;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
    }

    public void insert(String name, int num) {
        itemRepository.insertItem(name, num);
    }

    public List<Item> search(String name){
        return itemRepository.searchItem(name);
    }

    // TODO Add mapping calls between the UI and Database
}
