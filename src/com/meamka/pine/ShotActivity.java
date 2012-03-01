package com.meamka.pine;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: amka
 * Date: 29.02.12
 * Time: 19:21
 */
public class ShotActivity extends Activity {

    private CommentsAdapter commentsAdapter;
    private DribbleClient client;

    ArrayList<Comment> commentsList;

    private ListView commentsView;
    private TextView commentPageView;
    private TextView commentPagesCountView;
    private Button loadMoreBtn;

    private int commentsPage;
    private int commentsPagesCount;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shot_view_landscape);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        client = new DribbleClient();

        commentsList = new ArrayList<Comment>();
        commentsPage = 1;

        setupActivity();

    }

    private void setupActivity() {
        commentsView = (ListView)findViewById(R.id.comments_view);
        ImageView shotImageView = (ImageView)findViewById(R.id.shot_image);
        ImageView playerAvatarView = (ImageView)findViewById(R.id.avatar_image);
        TextView shotTitleView = (TextView)findViewById(R.id.shot_title);
        TextView shotPlayerName = (TextView)findViewById(R.id.player_name);
        TextView shotDate = (TextView)findViewById(R.id.shot_date);

        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footerView = inflater.inflate(R.layout.comments_list_footer, null, false);
        commentsView.addFooterView(footerView);

        loadMoreBtn = (Button)findViewById(R.id.load_more_btn);

        Bundle extras = getIntent().getExtras();
        final int shotId = extras.getInt("id");

        UrlImageViewHelper.setUrlDrawable(shotImageView, extras.getString("image_url"));
        UrlImageViewHelper.setUrlDrawable(playerAvatarView, extras.getString("avatar_url"));

        shotTitleView.setText(extras.getString("title"));
        shotPlayerName.setText(extras.getString("player_name"));
        shotDate.setText(extras.getString("date"));

        try {
            client.getShotComments(shotId, commentsHandler, commentsPage);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), R.string.error_parse_json, Toast.LENGTH_SHORT).show();
        }

        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    client.getShotComments(shotId, commentsHandler, ++commentsPage);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), R.string.error_parse_json, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.shot_view_landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.shot_view_portrait);
        }

        setupActivity();
    }

    @Override
    public boolean  onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shot_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getExtras().getString("url"));
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, getIntent().getExtras().getString("title"));
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                return true;
            case R.id.menu_view_in_browser:
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(getIntent().getExtras().getString("url")));
                startActivity(viewIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public JsonHttpResponseHandler commentsHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {

            int size = 0;
            try {
                JSONObject json_data = new JSONObject(response);
                JSONArray json_comments = json_data.getJSONArray("comments");

                size = json_comments.length();

                commentsPage = json_data.getInt("page");
                commentsPagesCount = json_data.getInt("pages");

                for (int i=0; i<json_comments.length(); i++)
                {
                    JSONObject json_comment = json_comments.getJSONObject(i);
                    JSONObject json_player = json_comment.getJSONObject("player");
                
                    Comment comment = new Comment();
                    comment.setId(json_comment.getInt("id"));
                    comment.setBody(json_comment.getString("body"));
                    comment.setLikesCount(json_comment.getInt("likes_count"));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
                    comment.setCreatedAt(format.parse(json_comment.getString("created_at")));

                    Player player = new Player();
                    player.setName(json_player.getString("name"));

                    String avatar_url = json_player.getString("avatar_url");
                    if (!avatar_url.startsWith("http")) {
                        player.setAvatarUrl(new URL("http://dribbble.com" + avatar_url));
                    } else {
                        player.setAvatarUrl(new URL(avatar_url));
                    }
                    comment.setPlayer(player);

                    commentsList.add(comment);
                }
                
            } catch (JSONException e){
                Toast.makeText(getApplicationContext(), R.string.error_parse_json, Toast.LENGTH_SHORT).show();
            } catch (MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "ParseException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            commentsAdapter = new CommentsAdapter(getApplicationContext(), commentsList);
            commentsView.setAdapter(commentsAdapter);
            commentsAdapter.notifyDataSetChanged();
            
            if (commentsPage < commentsPagesCount) {
                loadMoreBtn.setEnabled(true);
            } else {
                loadMoreBtn.setEnabled(false);
            }

            Toast.makeText(
                    getApplicationContext(),
                    "Loaded " + Integer.toString(size) + " comments",
                    Toast.LENGTH_SHORT
            ).show();
        }
    };
}