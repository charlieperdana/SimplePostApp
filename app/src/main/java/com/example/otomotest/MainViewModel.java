package com.example.otomotest;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.otomotest.api.ApiService;
import com.example.otomotest.api.RestApiBuilder;
import com.example.otomotest.model.Owner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Owner>> listData = new MutableLiveData<>();


    void setData() {

        AsyncHttpClient client = new AsyncHttpClient();

        final Owner owner = new Owner();

        final ArrayList<Owner> listItems = new ArrayList<>();
        String url = "https://n161.tech/api/dummyapi/post";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject data = list.getJSONObject(i);
                        Owner dataItems = new Owner(data);
                        listItems.add(dataItems);
                    }
                    listData.postValue(listItems);

                    Log.d("sukses", "sukseskok");
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                Log.d("sukses", "sukseskok");
            }
        });

    }

    void setPost() {

        final ArrayList<Owner> listItems = new ArrayList<>();
        ApiService apiService = new RestApiBuilder().getService();
        Call<Owner> dataPost = apiService.getAllPost();
        dataPost.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if (response.isSuccessful()) {
                    Log.d("berhasil", "apinya berhasil");
                    List<Owner> post = response.body().getItems();

                    for (int i = 0; i < post.size(); i++) {
                        int id = post.get(i).getId();
                        String photo = post.get(i).getPhoto();
                        String message = post.get(i).getMessage();
                        Owner owner = post.get(i).getOwner();
                        String name = owner.getName();
                        String title = owner.getTitle();
                        String itemPhoto = owner.getPhoto();
                        String lastName = owner.getLastName();
                        Log.d("berhasil item", name);

                        Owner postNew = new Owner(id, photo, title, name, lastName, message, itemPhoto);
                        listItems.add(postNew);

                    }


                }

                listData.postValue(listItems);
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }

    void setPostSearch(int idPost) {

        final ArrayList<Owner> listItems = new ArrayList<>();
        ApiService apiService = new RestApiBuilder().getService();
        Call<Owner> dataPost = apiService.searchPost(idPost);
        dataPost.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if (response.isSuccessful()) {
                    Log.d("berhasil", "apinya berhasil");

                        int id = response.body().getId();
                        String photo = response.body().getPhoto();
                        String message = response.body().getMessage();
                        Owner owner = response.body().getOwner();
                        String name = owner.getName();
                        String title = owner.getTitle();
                        String itemPhoto = owner.getPhoto();
                        String lastName = owner.getLastName();
                        Log.d("berhasil item", name);

                        Owner postNew = new Owner(id, photo, title, name, lastName, message, itemPhoto);
                        listItems.add(postNew);

                }

                listData.postValue(listItems);
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }

    LiveData<ArrayList<Owner>> getDatas() {
        return listData;
    }
}
