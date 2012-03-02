package com.meamka.pine;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 12.02.12
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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

    private Context mContext;
    private ArrayList<Comment> data;
    private Comment comment;

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

        comment = data.get(position);

        ImageView imageView = (ImageView)layout.findViewById(R.id.comment_player_avatar);
        UrlImageViewHelper.setUrlDrawable(imageView, comment.getPlayer().getAvatarUrl().toString());

        TextView commentBodyView = (TextView)layout.findViewById(R.id.comment_body);
        commentBodyView.setText(Html.fromHtml(comment.getBody()));
        commentBodyView.setLinksClickable(true);

        TextView commentPlayerView = (TextView)layout.findViewById(R.id.comment_player_name);
        commentPlayerView.setText(comment.getPlayer().getName());

        TextView commentDateView = (TextView)layout.findViewById(R.id.comment_created_at);
        commentDateView.setText(comment.getCreatedAt().toLocaleString());

        //@TODO: Click on comment author's avatar should open player's page
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent shotViewIntent = new Intent();
//                shotViewIntent.setClass(mContext, PlayerActivity.class);
//                shotViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                shotViewIntent.putExtra("url", comment.getPlayer().getUrl().toString());
//                shotViewIntent.putExtra("player_name", comment.getPlayer().getName());
//                shotViewIntent.putExtra("avatar_url", comment.getPlayer().getAvatarUrl().toString());
//                mContext.startActivity(shotViewIntent);
//            }
//        });
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
