package mc.apps.demos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mc.apps.demos.dao.Dao;
import mc.apps.demos.dao.InterventionDao;
import mc.apps.demos.dao.ProfilDao;
import mc.apps.demos.dao.TestDao;
import mc.apps.demos.dao.UserDao;
import mc.apps.demos.model.Test;
import mc.apps.demos.model.User;
import mc.apps.demos.ui.main.SectionsPagerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TabbedActivity extends AppCompatActivity {
    private static final String TAG = "tests" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        /*fab.setOnClickListener(view -> Snackbar
                        .make(view, "action..", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
        );*/

        //MySQL DB Request!
        dbRequest();
    }

    private void dbRequest() {
        /**
         * Database (MySQL) Tests
         */
   /*     UserDao dao = new UserDao();
        dao.list((data, msg) -> {
            Log.i(TAG, "*************************************");
            Log.i(TAG, "**************** List ***************");
            Log.i(TAG, "*************************************");
            Log.i(TAG, ""+data);
            Log.i(TAG, "*************************************");
        });
        dao.login("maelys@gmail.com","superv",
                (data, msg) -> {
                    Log.i(TAG, "======================================");
                    Log.i(TAG, "found : "+(data.isEmpty()?null:data.get(0)));
                    Log.i(TAG, "======================================");
                });

        ProfilDao dao2= new ProfilDao();
        dao2.list((data, msg) -> {
            Log.i(TAG, "*************************************");
            Log.i(TAG, ""+data);
            Log.i(TAG, "*************************************");
        });

        InterventionDao dao3= new InterventionDao();
        dao3.list((data, msg) -> {
            Log.i(TAG, "*************************************");
            Log.i(TAG, ""+data);
            Log.i(TAG, "*************************************");
        });*/

        TestDao dao = new TestDao();
        dao.list((data, msg) -> {
            Log.i(TAG, "*************************************");
            Log.i(TAG, "data : "+data);
            List<Test> items = dao.Deserialize(data, Test.class);
            Log.i(TAG, "items : "+items);
            Log.i(TAG, "*************************************");
        });
    }


}