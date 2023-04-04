package com.mz.befclient.data;

import com.mz.befclient.basket.FatoraDetail;

import java.util.List;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
@androidx.room.Dao
public interface Dao {
    @Insert
    void AddtoBasket(FatoraDetail orderItemList);

    @Query("select * from basket")
    List<FatoraDetail> getallproducts();

    @Query("DELETE FROM basket")
    void deleteAllproduct();

    @Query("delete from basket where product_id_fk =:id1")
    void DeleteProduct(String id1);
    @Update
    void editproduct(FatoraDetail fatoraDetail);
}
