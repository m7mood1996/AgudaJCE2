package com.example.agudajce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUsRecycle extends RecyclerView.Adapter<AboutUsRecycle.ViewHolder> {

    private static final String TAG = "AboutUsRecycle";
   // List<CycleObject> MainImageUploadInfoList;
    private ArrayList<String> mTexts = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public AboutUsRecycle(Context mContext, ArrayList<String> mTexts,ArrayList<String> mImages
                         // List<CycleObject> Templist
    ) {
        this.mTexts = mTexts;
        //this.MainImageUploadInfoList = Templist;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.aboutus_layout_list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext).asBitmap().load(mImages.get(i)).into(holder.image);
        //CycleObject UploadInfo = MainImageUploadInfoList.get(i);
        //holder.textimg.setText(UploadInfo.getImageName());
        //Glide.with(mContext).load(UploadInfo.getImageURL()).into(holder.image);
        //Loading image from Glide library.
        //Glide.with(context).load(UploadInfo.getImageURL()).into(holder.image);
        holder.textimg.setText(mTexts.get(i));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on an image : " + mTexts.get(i));
                Toast.makeText(mContext, mTexts.get(i), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
         return mImages.size();
        //return MainImageUploadInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // public CircleImageView image;
        //public TextView textimg;

       /* public ViewHolder(View itemView) {
            super(itemView);

            image = (CircleImageView) itemView.findViewById(R.id.image_view);

            textimg = (TextView) itemView.findViewById(R.id.textimg);
        }*/

        CircleImageView image;
        TextView textimg;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            textimg = itemView.findViewById(R.id.textimg);
        }
    }
}
