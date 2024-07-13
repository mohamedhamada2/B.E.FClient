package com.mz.befclient.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mz.befclient.R;
import com.mz.befclient.products.ProductActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferHolder> {
    Context context;
    List<Offer> offerList;

    public OfferAdapter(Context context, List<Offer> offerList) {
        this.context = context;
        this.offerList = offerList;
    }

    @NonNull
    @NotNull
    @Override
    public OfferHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_item,parent,false);
        return new OfferHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OfferHolder holder, int position) {
        holder.setData(offerList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("flag",2);
                intent.putExtra("offer_id",offerList.get(position).getId());
                context.startActivity(new Intent(intent));
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    class OfferHolder extends RecyclerView.ViewHolder{

        TextView txt_offer_name;

        TextView txt_offer_nesba;

        ImageView offer_img;
        public OfferHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txt_offer_name = itemView.findViewById(R.id.txt_offer_name);
            txt_offer_nesba = itemView.findViewById(R.id.txt_offer_nesba);
            offer_img = itemView.findViewById(R.id.offer_img);
        }

        public void setData(Offer offer) {
            txt_offer_name.setText(offer.getTitle());
            txt_offer_nesba.setText(offer.getValue());
            Picasso.get().load("https://b.f.e.one-click.solutions/uploads/images/thumbs/"+offer.getImage()).into(offer_img);

        }
    }
}
