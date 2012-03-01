package com.meamka.pine;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 12.02.12
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 12.02.12
 * Time: 8:37
 * To change this template use File | Settings | File Templates.
 */
public class CommentsAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<Comment> data;

    public CommentsAdapter(Context context, ArrayList<Comment> data) {
//        super(context, resource, ArrayList<Shot> data);
        this.mContext = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View layout;
        if (convertView == null) {
            layout = new View(mContext);

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.comment_layout, null);
        } else {
            layout = (View)convertView;
        }

        layout.setTag(position);

        Comment comment = data.get(position);

        ImageView imageView = (ImageView)layout.findViewById(R.id.comment_player_avatar);
        UrlImageViewHelper.setUrlDrawable(imageView, comment.getPlayer().getAvatarUrl().toString());

        TextView commentBodyView = (TextView)layout.findViewById(R.id.comment_body);
        commentBodyView.setText(comment.getBody());

        TextView commentPlayerView = (TextView)layout.findViewById(R.id.comment_player_name);
        commentPlayerView.setText(comment.getPlayer().getName());

        TextView commentDateView = (TextView)layout.findViewById(R.id.comment_created_at);
        commentDateView.setText(comment.getCreatedAt().toLocaleString());

//        if (comment.getPage() != 0 && comment.getPageCount() != 0) {
//            TextView commentsPages = (TextView)layout.findViewById(R.id.comments_pages);
//            commentsPages.setText(mContext.getString(R.string.page) +
//                    " " + Integer.toString(comment.getPage()) + " " +
//                    mContext.getString(R.string.of) +
//                    " " + Integer.toString(comment.getPageCount())
//            );
//        }

        return layout;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
