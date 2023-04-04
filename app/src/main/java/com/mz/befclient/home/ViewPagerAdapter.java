package com.mz.befclient.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mz.befclient.R;
import com.mz.befclient.Utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Image> list;

    public ViewPagerAdapter(Context context, List<Image> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.viewpageritem, null);
        ImageView imageView = view.findViewById(R.id.imageviewpager);
        /*Picasso.get()
                .load(list.get(position).getPhoto())
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(imageView);*/
        //imageView.setImageResource(list.get(position).getImg());
        //imageView.setImageResource(list.get(position).getImage());
        Picasso.get().load(Constants.BASE_URL+"uploads/thumbs/"+list.get(position).getImage()).into(imageView);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
