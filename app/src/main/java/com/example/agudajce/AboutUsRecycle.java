package com.example.agudajce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
    private ArrayList<CycleObject> cycleObjects = new ArrayList<>();

    private Context mContext;

    public AboutUsRecycle( ArrayList<CycleObject> cycleObjects,Context mContext){
        this.cycleObjects = cycleObjects;

        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.aboutus_layout_list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        CycleObject cycleObject =cycleObjects.get(i);

        String desc = cycleObject.getDes();
        holder.textimg.setText(desc);

        new AboutUsRecycle.DownloadImageFromInternet((ImageView) holder.image)
                .execute(cycleObject.getImageURL());
        //holder.image.setImageURI(Uri.parse(cycleObject.getImageURL()));

    }

    @Override
    public int getItemCount() {
         return cycleObjects.size();
        //return MainImageUploadInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        CircleImageView image;
        TextView textimg;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view_circle);
            textimg = itemView.findViewById(R.id.textimg);
        }
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;

        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
