package com.example.agudajce;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.io.InputStream;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder>{


    private List<Gallery_row> Gallery;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView1;
        public ImageView imageView2;

        public MyViewHolder(View view) {
            super(view);
            imageView1 = (ImageView) view.findViewById(R.id.imageView_re1);
            imageView2 = (ImageView) view.findViewById(R.id.imageView_re2);
        }
    }


    public GalleryAdapter(List<Gallery_row> gl) {
        this.Gallery = gl;
    }

    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_grid_album_layout, parent, false);

        return new GalleryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.MyViewHolder holder, int position) {
        Gallery_row gallery_row = Gallery.get(position);



        new GalleryAdapter.DownloadImageFromInternet((ImageView) holder.imageView1)
                .execute(gallery_row.getFirstImage());
        new GalleryAdapter.DownloadImageFromInternet((ImageView) holder.imageView2)
                .execute(gallery_row.getSecondImage());


    }


    @Override
    public int getItemCount() {
        return Gallery.size();
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
