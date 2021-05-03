package mc.apps.demos.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import mc.apps.demos.model.Test;
import mc.apps.demos.model.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Dao<T> {
    private static final String TAG = "tests" ;
    private static final String DB_API_URL = "https://mc69.go.yj.fr/db-request.php?" ;

    private String table;
    public Dao(String table) {
        this.table = table;
    }

    public List<T> Deserialize(List<?> data, Class<?> type) {
        Gson gs = new Gson();
        T item;
        List<T> items = new ArrayList<T>();
        for (Object json_object : data) {
            String js = gs.toJson(json_object);
            item = (T) gs.fromJson(js, type);
            items.add(item);
        }
        return items;
    }

    public interface OnSuccess{
        void result(List<?> items, String message);
    }
    public void list(OnSuccess onSuccess){
        String url = DB_API_URL+"list="+table;
        new Http2AsyncTask(onSuccess).execute(url);
    }
    public List<User> find(String whereClause, OnSuccess onSuccess){
        String url = DB_API_URL+"list="+table+"&"+whereClause;
        new Http2AsyncTask(onSuccess).execute(url);
        return null;
    }

    /**
     * Async Task
     */
    class Http2AsyncTask extends AsyncTask<String, Void, List<T>> {
        private final OkHttpClient client = new OkHttpClient();

        private OnSuccess onSuccess;
        public Http2AsyncTask(OnSuccess onSuccess) {
           this.onSuccess = onSuccess;
        }

        private List<T> dbRequest(String url) throws IOException {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            List<T> list = new Gson().fromJson(json, new TypeToken<List<?>>() {}.getType());
            return list;
        }
        @Override
        protected List<T> doInBackground(String... params) {
            String url = params[0];
            try {
                return dbRequest(url);
            } catch (IOException e) {
                Log.i(TAG, "error : "+e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<T> result) {
            this.onSuccess.result(result,"");
            //Log.i(TAG, "onPostExecute: "+result);
        }
    }



    /*public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsString());
            }
        }).create();
        return Arrays.asList(gson.fromJson(s, clazz));
    }*/
}
