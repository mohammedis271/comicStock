package com.example.superman_application.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superman_application.DTO.BrowseDTO;
import com.example.superman_application.DTO.IssueDTO;
import com.example.superman_application.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private ArrayList<IssueDTO> issueDTOArrayList = new ArrayList<>();
    private Context mContext;


    public RecycleAdapter( Context mContext,ArrayList<IssueDTO> issueDTOArrayList) {
        this.mContext = mContext;
        this.issueDTOArrayList = issueDTOArrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_browse,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_book_title.setText(issueDTOArrayList.get(position).getTitle());
        holder.img_book_thumbnail.setImageResource(R.drawable.comic);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mContext,Book_Activity.class);
//
//                // passing data to the book activity
//                intent.putExtra("Title",mData.get(position).getTitle());
//                intent.putExtra("Description",mData.get(position).getDescription());
//                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
//                // start the activity
//                mContext.startActivity(intent);
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return issueDTOArrayList.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id) ;
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}

