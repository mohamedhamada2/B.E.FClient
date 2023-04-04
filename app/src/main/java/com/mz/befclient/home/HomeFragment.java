package com.mz.befclient.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mz.befclient.R;
import com.mz.befclient.basket.BasketActivity;
import com.mz.befclient.categories.CategoriesFragment;
import com.mz.befclient.data.DatabaseClass;
import com.mz.befclient.databinding.FragmentHomeBinding;
import com.mz.befclient.main.MainActivity;
import com.mz.befclient.products.ProductActivity;

import java.util.List;


public class HomeFragment extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    HomeViewModel homeViewModel;
    CategoryInHomeAdapter categoryInHomeAdapter;
    OfferAdapter offerAdapter;
    LinearLayoutManager layoutManager,layoutManager2;
    private ImageView[] dots;
    GridLayoutManager gridLayoutManager,gridLayoutManager2;
    private boolean isloading;
    private int pastvisibleitem,visibleitemcount,totalitemcount,previous_total=0;
    int view_threshold = 20;
    int page =1 ;
    DatabaseClass databaseClass;
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        homeViewModel = new HomeViewModel(getContext(), this);
        fragmentHomeBinding.setHomeviemodel(homeViewModel);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager2 = new GridLayoutManager(getActivity(), 2);
        databaseClass = Room.databaseBuilder(getActivity().getApplicationContext(), DatabaseClass.class, "basket").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        homeViewModel.getCategories();
        homeViewModel.getSlider();
        homeViewModel.get_products();
        homeViewModel.get_offers();
        mainActivity =(MainActivity) getActivity();
        fragmentHomeBinding.txtBasket.setText(databaseClass.getDao().getallproducts().size() + "");
        View view = fragmentHomeBinding.getRoot();
        fragmentHomeBinding.txtSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView homeNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.homeBottomnavigation);
                homeNavigationView.setSelectedItemId(R.id.categories);
                CategoriesFragment nextFrag = new CategoriesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "categories")
                        .addToBackStack(null)
                        .commit();
            }
        });
        fragmentHomeBinding.txtSeeMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("flag", 3);
                startActivity(intent);
            }
        });
        fragmentHomeBinding.productsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleitemcount = gridLayoutManager.getChildCount();
                totalitemcount = gridLayoutManager.getItemCount();
                pastvisibleitem = gridLayoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (isloading) {
                        if (totalitemcount > previous_total) {
                            isloading = false;
                            previous_total = totalitemcount;
                        }
                    }
                    if (!isloading && (totalitemcount - visibleitemcount) <= pastvisibleitem + view_threshold) {
                        page++;
                        homeViewModel.PerformPagination();
                        isloading = true;
                    }
                }
            }
        });
        fragmentHomeBinding.basketCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BasketActivity.class));
            }
        });
        fragmentHomeBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    //Toast.makeText(ProductActivity.this, "search", Toast.LENGTH_SHORT).show();
                    fragmentHomeBinding.allScroll.setVisibility(View.VISIBLE);
                    fragmentHomeBinding.allProductsRecycler.setVisibility(View.GONE);
                } else {
                    fragmentHomeBinding.allScroll.setVisibility(View.GONE);
                    fragmentHomeBinding.allProductsRecycler.setVisibility(View.VISIBLE);
                    homeViewModel.search_all_products(charSequence.toString(), 1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fragmentHomeBinding.allProductsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleitemcount = gridLayoutManager2.getChildCount();
                totalitemcount = gridLayoutManager2.getItemCount();
                pastvisibleitem = gridLayoutManager2.findFirstVisibleItemPosition();
                if(dy>0){
                    if(isloading){
                        if(totalitemcount>previous_total){
                            isloading = false;
                            previous_total = totalitemcount;
                        }
                    }
                    if(!isloading &&(totalitemcount-visibleitemcount)<= pastvisibleitem+view_threshold){
                        page++;
                        homeViewModel.PerformPagination3(page);
                        isloading = true;
                    }
                }
            }
        });
        return view;
    }

    public void init_category(List<Datum> categoryList) {
        categoryInHomeAdapter = new CategoryInHomeAdapter(categoryList,getContext());
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
        fragmentHomeBinding.categoryRecycler.setHasFixedSize(true);
        fragmentHomeBinding.categoryRecycler.setAdapter(categoryInHomeAdapter);
        fragmentHomeBinding.categoryRecycler.setLayoutManager(layoutManager);
    }

    public void init_slider(List<Image> sliderList) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext(), sliderList);
        fragmentHomeBinding.sliderviewPager.setAdapter(viewPagerAdapter);
        dots = new ImageView[sliderList.size()];
        for (int i = 0; i < sliderList.size(); i++) {
            fragmentHomeBinding.sliderviewPager.startAutoScroll();
            fragmentHomeBinding.sliderviewPager.setInterval(2500);
            fragmentHomeBinding.sliderviewPager.setCycle(true);
            fragmentHomeBinding.sliderviewPager.setStopScrollWhenTouch(true);
            dots[i] = new ImageView(mainActivity);
            dots[i].setImageDrawable(ContextCompat.getDrawable(mainActivity.getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            fragmentHomeBinding.indecator.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(mainActivity.getApplicationContext(), R.drawable.active_dot));

        fragmentHomeBinding.sliderviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < sliderList.size(); i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(mainActivity.getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(mainActivity.getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void init_products(ProductsAdapter productsAdapter) {
        fragmentHomeBinding.productsRecycler.setHasFixedSize(true);
        fragmentHomeBinding.productsRecycler.setAdapter(productsAdapter);
        fragmentHomeBinding.productsRecycler.setLayoutManager(gridLayoutManager);
    }

    public void init_offers(List<Offer> data) {
        offerAdapter = new OfferAdapter(getActivity(),data);
        fragmentHomeBinding.offersRecycler.setHasFixedSize(true);
        fragmentHomeBinding.offersRecycler.setAdapter(offerAdapter);
        layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        fragmentHomeBinding.offersRecycler.setLayoutManager(layoutManager2);

    }

    public void setbasketsize(String s) {
        fragmentHomeBinding.txtBasket.setText(s);
    }

    public void init_all_products(ProductsAdapter productsAdapter) {
        //Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
        fragmentHomeBinding.allProductsRecycler.setHasFixedSize(true);
        fragmentHomeBinding.allProductsRecycler.setAdapter(productsAdapter);
        fragmentHomeBinding.allProductsRecycler.setLayoutManager(gridLayoutManager2);
    }
}