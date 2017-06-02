package com.example.apple.parkingsaver.adapter_ParkingSaver;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.apple.parkingsaver.R;
import com.example.apple.parkingsaver.model.Item;

import java.util.List;

/**
 * Created by apple on 2017/5/31.
 */

public class AdapterParkingSaver extends RecyclerView.Adapter<AdapterParkingSaver.ParkingSaverHolder>{
    private List<Item> listData;
    private LayoutInflater inflater;

//================about ItemClickCallback==========//
    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
      //  void onItemClick(int p);   //============可能需要九个button的
        void onItemClick1(int p);
        void onItemClick2(int p);
        void onItemClick3(int p);
        void onItemClick4(int p);
        void onItemClick5(int p);
        void onItemClick6(int p);
        void onItemClick7(int p);
        void onItemClick8(int p);
        void onItemClick9(int p);

    }
    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }



    public AdapterParkingSaver(List<Item> listData, Context c) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(c);

    }

    @Override
    public ParkingSaverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_parkingsavers,parent,false);
        return new ParkingSaverHolder(view);
    }

    @Override
    //    7B68EE
    public void onBindViewHolder(ParkingSaverHolder holder, int position) {
        Item item = listData.get(position);
        holder.tv_parkingSaverID.setText(item.getPs_ID());
//        if(item.getTimeSectionFrom0().isIfReservation() == "true") {
//            holder.bt_Row_1_Col_1.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom0().isIfReservation() == "false") {
//            holder.bt_Row_1_Col_1.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_1_Col_1.setText(item.getTimeSectionFrom0().getTimeSection());


//        if(item.getTimeSectionFrom6().isIfReservation() == "true") {
//            holder.bt_Row_1_Col_2.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom6().isIfReservation() == "false") {
//            holder.bt_Row_1_Col_2.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_1_Col_2.setText(item.getTimeSectionFrom6().getTimeSection());



//        if(item.getTimeSectionFrom8().isIfReservation() == "true") {
//            holder.bt_Row_1_Col_3.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom8().isIfReservation() == "false") {
//            holder.bt_Row_1_Col_3.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_1_Col_3.setText(item.getTimeSectionFrom8().getTimeSection());


//        if(item.getTimeSectionFrom10().isIfReservation() == "true") {
//            holder.bt_Row_2_Col_1.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom10().isIfReservation() == "false") {
//            holder.bt_Row_2_Col_1.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_2_Col_1.setText(item.getTimeSectionFrom10().getTimeSection());


//        if(item.getTimeSectionFrom12().isIfReservation() == "true") {
//            holder.bt_Row_2_Col_2.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom12().isIfReservation() == "false") {
//            holder.bt_Row_2_Col_2.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_2_Col_2.setText(item.getTimeSectionFrom12().getTimeSection());


//        if(item.getTimeSectionFrom14().isIfReservation() == "true") {
//            holder.bt_Row_2_Col_3.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom14().isIfReservation() == "false") {
//            holder.bt_Row_2_Col_3.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_2_Col_3.setText(item.getTimeSectionFrom14().getTimeSection());


//        if(item.getTimeSectionFrom16().isIfReservation() == "true") {
//            holder.bt_Row_3_Col_1.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom16().isIfReservation() == "false") {
//            holder.bt_Row_3_Col_1.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_3_Col_1.setText(item.getTimeSectionFrom16().getTimeSection());


//        if(item.getTimeSectionFrom18().isIfReservation() == "true") {
//            holder.bt_Row_3_Col_2.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom18().isIfReservation() == "false") {
//            holder.bt_Row_3_Col_2.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_3_Col_2.setText(item.getTimeSectionFrom18().getTimeSection());


//        if(item.getTimeSectionFrom21().isIfReservation() == "true") {
//            holder.bt_Row_3_Col_3.setBackgroundColor(Color.parseColor("#E43F3F"));
//        }
//        if(item.getTimeSectionFrom21().isIfReservation() == "false") {
//            holder.bt_Row_3_Col_3.setBackgroundColor(Color.parseColor("#7B68EE"));
//        }
        holder.bt_Row_3_Col_3.setText(item.getTimeSectionFrom21().getTimeSection());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    //========= inner class for view holder============//
    class ParkingSaverHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tv_parkingSaverID;
        private Button bt_Row_1_Col_1;
        private Button bt_Row_1_Col_2;
        private Button bt_Row_1_Col_3;
        private Button bt_Row_2_Col_1;
        private Button bt_Row_2_Col_2;
        private Button bt_Row_2_Col_3;
        private Button bt_Row_3_Col_1;
        private Button bt_Row_3_Col_2;
        private Button bt_Row_3_Col_3;

        private View container;


        public ParkingSaverHolder(View itemView) {
            super(itemView);
            tv_parkingSaverID =(TextView)itemView.findViewById(R.id.parkingSaverID);
            bt_Row_1_Col_1 = (Button)itemView.findViewById(R.id.Row_1_Col_1);
            bt_Row_1_Col_1.setOnClickListener(this);
            bt_Row_1_Col_2 = (Button)itemView.findViewById(R.id.Row_1_Col_2);
            bt_Row_1_Col_2.setOnClickListener(this);
            bt_Row_1_Col_3 = (Button)itemView.findViewById(R.id.Row_1_Col_3);
            bt_Row_1_Col_3.setOnClickListener(this);
            bt_Row_2_Col_1 = (Button)itemView.findViewById(R.id.Row_2_Col_1);
            bt_Row_2_Col_1.setOnClickListener(this);
            bt_Row_2_Col_2 = (Button)itemView.findViewById(R.id.Row_2_Col_2);
            bt_Row_2_Col_2.setOnClickListener(this);
            bt_Row_2_Col_3 = (Button)itemView.findViewById(R.id.Row_2_Col_3);
            bt_Row_2_Col_3.setOnClickListener(this);
            bt_Row_3_Col_1 = (Button)itemView.findViewById(R.id.Row_3_Col_1);
            bt_Row_3_Col_1.setOnClickListener(this);
            bt_Row_3_Col_2 = (Button)itemView.findViewById(R.id.Row_3_Col_2);
            bt_Row_3_Col_2.setOnClickListener(this);
            bt_Row_3_Col_3 = (Button)itemView.findViewById(R.id.Row_3_Col_3);
            bt_Row_3_Col_3.setOnClickListener(this);

            container = itemView.findViewById(R.id.container_items_root);


        }

        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.Row_1_Col_1) {
                itemClickCallback.onItemClick1(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_1_Col_2) {
                itemClickCallback.onItemClick2(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_1_Col_3) {
                itemClickCallback.onItemClick3(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_2_Col_1){
                itemClickCallback.onItemClick4(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_2_Col_2) {
                itemClickCallback.onItemClick5(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_2_Col_3){
                itemClickCallback.onItemClick6(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_3_Col_1) {
                itemClickCallback.onItemClick7(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_3_Col_2) {
                itemClickCallback.onItemClick8(getAdapterPosition());
            }
            if(v.getId() == R.id.Row_3_Col_3) {
                itemClickCallback.onItemClick9(getAdapterPosition());
            }


        }
    }
}


//            if (v.getId() == R.id.cont_item_root){
//                    itemClickCallback.onItemClick(getAdapterPosition());
//                    } else {
//                    itemClickCallback.onSecondaryIconClick(getAdapterPosition());
//                    }
//                    }