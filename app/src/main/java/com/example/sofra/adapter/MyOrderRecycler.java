package com.example.sofra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sofra.R;
import com.example.sofra.data.model.order.Item;
import com.example.sofra.data.model.order.OrderData;

import java.util.ArrayList;
import java.util.List;

public class MyOrderRecycler extends RecyclerView.Adapter<MyOrderRecycler.MyViewHolder> {
    Context context;
    List<OrderData> orderData = new ArrayList<>();
    String type = null;

    public MyOrderRecycler(Context context, List<OrderData> orderData, String type) {
        this.context = context;
        this.orderData = orderData;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_order_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        List<Item> items = orderData.get(position).getItems();
        StringBuilder name = new StringBuilder();

        for (Item item : items) {
            name.append(item.getName() + ".");
        }
        Glide.with(context).load(orderData.get(position).getRestaurant().getPhotoUrl()).into(myViewHolder.orderImage);
        myViewHolder.orderName.setText(name);
        myViewHolder.orderPrice.setText(orderData.get(position).getCost());
        myViewHolder.orderDelivery.setText(orderData.get(position).getDeliveryCost());
        myViewHolder.orderTotal.setText(orderData.get(position).getTotal());
        myViewHolder.orderNumber.setText(orderData.get(position).getId().toString());
        if (type.equalsIgnoreCase("current")) {
            myViewHolder.orderLike.setVisibility(View.GONE);
            myViewHolder.orderDislike.setVisibility(View.GONE);
        }else
        {
            myViewHolder.orderLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Like :)",Toast.LENGTH_LONG).show();
                }
            });
            myViewHolder.orderDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"DisLike :(",Toast.LENGTH_LONG).show();

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return orderData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView orderImage;
        private TextView orderName;
        private TextView orderPrice;
        private TextView orderDelivery;
        private TextView orderTotal;
        private TextView orderNumber;
        private Button orderLike;
        private Button orderDislike;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImage = (ImageView) itemView.findViewById(R.id.My_Order_ImageView_Order);
            orderName = (TextView) itemView.findViewById(R.id.My_Order_Tv_Order_Name);
            orderPrice = (TextView) itemView.findViewById(R.id.My_Order_Tv_Order_Price);
            orderDelivery = (TextView) itemView.findViewById(R.id.My_Order_Tv_Order_Delivery);
            orderTotal = (TextView) itemView.findViewById(R.id.My_Order_Tv_Order_Total);
            orderNumber = (TextView) itemView.findViewById(R.id.My_Order_Tv_Order_Number);
            orderLike = (Button) itemView.findViewById(R.id.My_Order_Btn_Order_Like);
            orderDislike = (Button) itemView.findViewById(R.id.My_Order_Btn_Order_Dislike);
        }
    }
}
