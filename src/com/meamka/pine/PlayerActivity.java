package com.meamka.pine;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by IntelliJ IDEA.
 * User: amka
 * Date: 02.03.12
 * Time: 12:00
 */
public class PlayerActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_view_landscape);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupActivity();
    }

    private void setupActivity() {
        ImageView avatarView = (ImageView)findViewById(R.id.player_avatar);
        TextView playerNameView = (TextView)findViewById(R.id.player_name);
        
        
        Bundle extras = getIntent().getExtras();

        UrlImageViewHelper.setUrlDrawable(avatarView, extras.getString("avatar_url"));
        playerNameView.setText(extras.getString("player_name"));
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
}