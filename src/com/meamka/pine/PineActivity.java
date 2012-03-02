package com.meamka.pine;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.*;
//import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
//import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
//import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PineActivity extends Activity
{
    public DribbleClient client;
    private ShotAdapter popularAdapter;
    private ShotAdapter everyoneAdapter;
    private ShotAdapter debutAdapter;

    private GridView popularShotsView;
    private GridView everyoneShotsView;
    private GridView debutShotsView;

    private Button loadMoreShotsBtn;
    
    public int activeTab;

    final int TAG_POPULAR = 1;
    final int TAG_EVERYONE = 2;
    final int TAG_DEBUT = 3;
    
    private int popularPage = 1;
    private int everyonePage = 1;
    private int debutPage = 1;

    public boolean popularLoaded = false;
    public boolean everyoneLoaded = false;
    public boolean debutLoaded = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        client = new DribbleClient();
        activeTab = TAG_POPULAR;

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        loadMoreShotsBtn = (Button)findViewById(R.id.load_more_shots);

        loadMoreShotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    switch (activeTab) {
                        case TAG_POPULAR:
                            client.getPopularShots(shotsResponseHandler, ++popularPage);
                            break;
                        case TAG_EVERYONE:
                            client.getEveryoneShots(shotsResponseHandler, ++everyonePage);
                            break;
                        case TAG_DEBUT:
                            client.getDebutsShots(shotsResponseHandler, ++debutPage);
                            break;
                    }
                } catch (JSONException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.error_parse_json), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        // Create and assing tabs
        Tab tabA = actionBar.newTab().setText(getString(R.string.popular)).setTag(TAG_POPULAR);
        Tab tabB = actionBar.newTab().setText(getString(R.string.everyone)).setTag(TAG_EVERYONE);
        Tab tabC = actionBar.newTab().setText(getString(R.string.debut)).setTag(TAG_DEBUT);

        Fragment fragmentPopular = new PopularShotsFragmentTab();
        Fragment fragmentEveryone = new EveryoneShotsFragmentTab();
        Fragment fragmentDebut = new DebutShotsFragmentTab();

        tabA.setTabListener(new GridViewTabsListener(fragmentPopular));
        tabB.setTabListener(new GridViewTabsListener(fragmentEveryone));
        tabC.setTabListener(new GridViewTabsListener(fragmentDebut));

        actionBar.addTab(tabA);
        actionBar.addTab(tabB);
        actionBar.addTab(tabC);

        // Load data for 1st screen when app is loading
        try {
            switch (activeTab) {
                case TAG_POPULAR:
                    client.getPopularShots(shotsResponseHandler, popularPage);
                    break;
                case TAG_EVERYONE:
                    client.getEveryoneShots(shotsResponseHandler, everyonePage);
                    break;
                case TAG_DEBUT:
                    client.getDebutsShots(shotsResponseHandler, debutPage);
                    break;
            }
        } catch (JSONException e) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.error_parse_json), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean  onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_reload:
                try {
                    switch (activeTab) {
                        case TAG_POPULAR:
                            client.getPopularShots(shotsResponseHandler, popularPage);
                            break;
                        case TAG_EVERYONE:
                            client.getEveryoneShots(shotsResponseHandler, everyonePage);
                            break;
                        case TAG_DEBUT:
                            client.getDebutsShots(shotsResponseHandler, debutPage);
                            break;
                    }
                    return true;
                } catch (JSONException e) {
                    return false;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Handle client response
     */
    public JsonHttpResponseHandler shotsResponseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(String response) {
            ArrayList<Shot> shots = new ArrayList<Shot>();

            int page = 1;
            int pages = 1;

            try {
                JSONObject  json_data = new JSONObject(response);
                JSONArray json_shots = json_data.getJSONArray("shots");

                page = json_data.getInt("page");
                pages = json_data.getInt("pages");

                Log.d("PINE_DEBUG", json_data.getString("page"));
                Log.d("PINE_DEBUG", json_data.getString("pages"));

                for (int i=0; i<json_shots.length(); i++)
                {
                    JSONObject json_shot = json_shots.getJSONObject(i);
                    JSONObject json_player = json_shot.getJSONObject("player");

                    Shot shot = new Shot();
                    shot.setId(json_shot.getInt("id"));
                    shot.setTitle(json_shot.getString("title"));
                    shot.setUrl(new URL(json_shot.getString("url")));
//                    shot.setShortUrl(new URL(json_shot.getString("short_url")));
                    shot.setImageUrl(new URL(json_shot.getString("image_url")));
                    shot.setImageTeaserUrl(new URL(json_shot.getString("image_teaser_url")));
//                    shot.setWidth(json_shot.getInt("width"));
//                    shot.setHeight(json_shot.getInt("height"));
                    shot.setViewsCount(json_shot.getInt("views_count"));
                    shot.setLikesCount(json_shot.getInt("likes_count"));
                    shot.setCommentsCount(json_shot.getInt("comments_count"));
//                    shot.setRebounds_count(json_shot.getInt("rebounds_count"));
//                    shot.setRebound_source_id(json_shot.getInt("rebound_source_id"));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
                    shot.setCreatedAt(format.parse(json_shot.getString("created_at")));
                    
                    Player player = new Player();
                    player.setName(json_player.getString("name"));
                    player.setUrl(new URL(json_player.getString("url")));
                    
                    String avatar_url = json_player.getString("avatar_url");
                    
                    if (!avatar_url.startsWith("http")) {
                        player.setAvatarUrl(new URL("http://dribbble.com" + avatar_url));
                    } else {
                        player.setAvatarUrl(new URL(avatar_url));
                    }
                    shot.setPlayer(player);

                    shots.add(shot);
                }

            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), R.string.error_parse_json, Toast.LENGTH_SHORT).show();
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "MalformedURLException" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "ParseException", Toast.LENGTH_SHORT).show();
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            
            switch (activeTab) {
                case TAG_POPULAR:
                    popularShotsView = (GridView)findViewById(R.id.popular_shots_view);
                    popularAdapter = new ShotAdapter(getApplicationContext(), shots);
                    if (popularShotsView.getAdapter() == null) {
                        popularShotsView.setAdapter(popularAdapter);
                    } else {
                        popularAdapter.notifyDataSetChanged();
                    }
                    popularShotsView.setOnItemClickListener(shotClickListener);
                    // Set current page
                    popularPage = page;
                    // Hide Load More btn if all pages reached.
                    break;

                case TAG_EVERYONE:
                    everyoneShotsView = (GridView)findViewById(R.id.everyone_shots_view);
                    if (everyoneShotsView.getAdapter() == null) {
                        everyoneAdapter = new ShotAdapter(getApplicationContext(), shots);
                    } else  {
                        everyoneShotsView.setAdapter(everyoneAdapter);
                    }
                    everyoneAdapter.notifyDataSetChanged();
                    everyoneShotsView.setOnItemClickListener(shotClickListener);
                    everyonePage = page;
                    break;

                case TAG_DEBUT:
                    debutShotsView= (GridView)findViewById(R.id.debut_shots_view);
                    if (debutShotsView.getAdapter() == null) {
                        debutAdapter = new ShotAdapter(getApplicationContext(), shots);
                        debutShotsView.setAdapter(debutAdapter);
                    } else {
                        debutAdapter.notifyDataSetChanged();
                    }
                    debutShotsView.setOnItemClickListener(shotClickListener);
                    debutPage = page;
                    break;
            }
        }

        @Override
        public void onFailure(Throwable error) {
        }

        @Override
        public void onStart() {
        }

        @Override
        public void onFinish() {
        }
    };

    protected class GridViewTabsListener implements ActionBar.TabListener {
        private Fragment fragment;

        public GridViewTabsListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (!fragment.isAdded()) {
                ft.add(R.id.fragment_place, fragment, null);
            }
            
            if (fragment.isHidden()) {
                ft.show(fragment);
            }

            activeTab = (Integer)tab.getTag();

            try {
                switch (activeTab) {
                    case TAG_POPULAR:
                        if (!popularLoaded) {
                            client.getPopularShots(shotsResponseHandler, popularPage);
                            popularLoaded = true;
                        }
                        break;
                    case TAG_EVERYONE:
                        if (!everyoneLoaded) {
                            client.getEveryoneShots(shotsResponseHandler, everyonePage);
                            everyoneLoaded = true;
                        }
                        break;
                    case TAG_DEBUT:
                        if (!debutLoaded) {
                            client.getDebutsShots(shotsResponseHandler, debutPage);
                            debutLoaded = true;
                        }
                        break;
                }
            } catch (JSONException e) {
                Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
            //To change body of implemented methods use File | Settings | File Templates.
//            fragmentTransaction.hide(fragment);
            fragmentTransaction.hide(fragment);
        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
            // User selected the already selected tab. Usually do nothing.
        }
    }

    private AdapterView.OnItemClickListener shotClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            Shot shot = null;
            switch (activeTab) {
                case TAG_POPULAR:
                    shot = (Shot)popularAdapter.getItem(position);
                    break;

                case TAG_EVERYONE:
                    shot = (Shot)everyoneAdapter.getItem(position);
                    break;

                case TAG_DEBUT:
                    shot = (Shot)debutAdapter.getItem(position);
                    break;
            }

            Intent shotViewIntent = new Intent();
            shotViewIntent.setClass(getApplicationContext(), ShotActivity.class);
            shotViewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            shotViewIntent.putExtra("id", shot.getId());
            shotViewIntent.putExtra("title", shot.getTitle());
            shotViewIntent.putExtra("image_url", shot.getImageUrl().toString());
            shotViewIntent.putExtra("url", shot.getUrl().toString());
            shotViewIntent.putExtra("date", shot.getCreatedAt().toLocaleString());
            shotViewIntent.putExtra("player_url", shot.getPlayer().getUrl().toString());
            shotViewIntent.putExtra("player_name", shot.getPlayer().getName());
            shotViewIntent.putExtra("avatar_url", shot.getPlayer().getAvatarUrl().toString());
            startActivity(shotViewIntent);
        }
    };
}
