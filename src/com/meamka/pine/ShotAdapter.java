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
import android.widget.*;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 12.02.12
 * Time: 8:37
 * To change this template use File | Settings | File Templates.
 */
public class ShotAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<Shot> data;

    public ShotAdapter(Context context, ArrayList<Shot> data) {
//        super(context, resource, ArrayList<Shot> data);
        this.mContext = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        if (convertView == null) {
            grid = new View(mContext);

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.shot_grid_item, null);
        } else {
            grid = (View)convertView;
        }

        grid.setTag(position);

        ImageView imageView = (ImageView)grid.findViewById(R.id.teaser_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(2, 2, 2, 2);
        UrlImageViewHelper.setUrlDrawable(imageView, data.get(position).getImageTeaserUrl().toString());

        TextView shotTitleView = (TextView)grid.findViewById(R.id.shot_title);
        shotTitleView.setText(data.get(position).getTitle());

        TextView shotViewsView = (TextView)grid.findViewById(R.id.shot_views_count);
        shotViewsView.setText(Integer.toString(data.get(position).getViewsCount()));

        TextView shotCommentsView = (TextView)grid.findViewById(R.id.shot_comments_count);
        shotCommentsView.setText(Integer.toString(data.get(position).getCommentsCount()));


//        grid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //To change body of implemented methods use File | Settings | File Templates.
//                int position = (Integer) view.getTag();
//
//                Shot shot = data.get(position);
//
//                Intent shotViewIntent = new Intent();
//                shotViewIntent.putExtra("shot_title", shot.getTitle());
//                shotViewIntent.putExtra("shot_image_url", shot.getImageUrl().toString());
//                mContext.startActivity(shotViewIntent);
//            }
//        });

        return grid;
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
