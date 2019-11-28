package com.example.nyt;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nyt.model.BuyLink;

import java.util.List;

public class BuyLinkAdapter extends RecyclerView.Adapter<BuyLinkAdapter.BuyLinkViewHolder> {
    public static final String INTENT_URL_EXTRA = "URL";
    private List<BuyLink> data;

    @NonNull
    @Override
    public BuyLinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_link, parent, false);
        return new BuyLinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BuyLinkViewHolder holder, int position) {
        final BuyLink currentBuyLink = data.get(position);

        holder.buyLinkSource.setText(currentBuyLink.getName());
        holder.buyLinkUrl.setText(currentBuyLink.getUrl());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentBuyLink.getUrl()));
                holder.view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<BuyLink> data) {
        this.data = data;
    }

    public class BuyLinkViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView buyLinkSource;
        private TextView buyLinkUrl;

        public BuyLinkViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            buyLinkSource = itemView.findViewById(R.id.buyLinkSource);
            buyLinkUrl = itemView.findViewById(R.id.buyLinkUrl);
        }
    }
}
