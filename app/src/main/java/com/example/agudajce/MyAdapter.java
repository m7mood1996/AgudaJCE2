package com.example.agudajce;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Post> postList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView_re);
            imageView = (ImageView) view.findViewById(R.id.imageView_re);
        }
    }


    public MyAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_list_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Post post = postList.get(position);
        ////////////////
        String postDes = post.getDescription();

        ArrayList<String> links =pullLinks(postDes);

        int i =0;
        for(;i<links.size();i++){


            String link = "<a href='"+links.get(i)+"'>" +links.get(i) +"</a>";

            postDes =postDes.replace(links.get(i),link);

        }

        holder.textView.setText(Html.fromHtml(postDes));
        holder.textView.setMovementMethod(LinkMovementMethod.getInstance());

        new DownloadImageFromInternet((ImageView) holder.imageView)
                .execute(post.getImage());

    }

    @Override
    public int getItemCount() {
        return postList.size();
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


    public ArrayList<String> pullLinks(String text) {
        ArrayList<String> links = new ArrayList<String>();

        String regex = "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
                "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
                "|mil|biz|info|mobi|name|aero|jobs|museum" +
                "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            links.add(urlStr);
        }
        return links;
    }
}