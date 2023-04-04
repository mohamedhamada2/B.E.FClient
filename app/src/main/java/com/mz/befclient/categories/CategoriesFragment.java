package com.mz.befclient.categories;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mz.befclient.R;
import com.mz.befclient.basket.BasketActivity;
import com.mz.befclient.data.DatabaseClass;
import com.mz.befclient.databinding.FragmentCategoriesBinding;
import com.mz.befclient.home.Category;
import com.mz.befclient.products.ProductActivity;

import java.util.List;


public class CategoriesFragment extends Fragment {
    FragmentCategoriesBinding fragmentCategoriesBinding;
    CategoryViewModel categoryViewModel;
    CategoryAdapter categoryAdapter;
    LinearLayoutManager layoutManager;
    DatabaseClass databaseClass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCategoriesBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories, container, false);
        categoryViewModel = new CategoryViewModel(getContext(),this);
        categoryViewModel.getCategories();
        fragmentCategoriesBinding.setCategoryviewmodel(categoryViewModel);
        View view = fragmentCategoriesBinding.getRoot();
        databaseClass =  Room.databaseBuilder(getContext().getApplicationContext(), DatabaseClass.class,"basket").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        fragmentCategoriesBinding.txtBasket.setText(databaseClass.getDao().getallproducts().size()+"");
        fragmentCategoriesBinding.basketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BasketActivity.class));
            }
        });
        return view;
    }

    public void init_category(List<Datum> categoryList) {
        fragmentCategoriesBinding.categoryRecycler.setHasFixedSize(true);
        categoryAdapter = new CategoryAdapter(categoryList,getContext());
        fragmentCategoriesBinding.categoryRecycler.setAdapter(categoryAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        fragmentCategoriesBinding.categoryRecycler.setLayoutManager(layoutManager);
    }
}