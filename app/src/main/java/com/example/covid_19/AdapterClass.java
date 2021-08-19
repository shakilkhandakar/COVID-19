package com.example.covid_19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.DataHolder> {
    Context context;
    List<Data> dataList;
    private List<Data> dataFilterList;

    public AdapterClass(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.dataFilterList = dataList;
}

    @NonNull
    @Override
    public AdapterClass.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_view, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.DataHolder holder,int  position) {
        holder.textView.setText(dataFilterList.get(position).getCountry());
        Glide.with(context).load(dataFilterList.get(position).getFlag()).into(holder.imageView);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,DetailsActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                        .putExtra("position",position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataFilterList.size();
    }

    public static class DataHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageFlag);
            textView = itemView.findViewById(R.id.tvCountryName);
        }
    }
    /* public void getFilter(ArrayList<Data> filterList) {
           dataList=filterList;
           notifyDataSetChanged();
       }*/

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = dataList.size();
                    filterResults.values = dataList;
                }else{
                    List<Data> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(Data itemsModel:dataList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                dataFilterList = (List<Data>) results.values;
                AffectedCountries.dataList = (List<Data>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
