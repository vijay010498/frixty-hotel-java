package com.frixty.frixtyhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.frixty.frixtyhotel.APIs.APIEndpoints;
import com.frixty.frixtyhotel.Models.MessageBodyResponse;
import com.frixty.frixtyhotel.APIs.RequestBody.VerifyAuthTokenBody;
import com.frixty.frixtyhotel.APIs.ServerInstance;
import com.frixty.frixtyhotel.sharedPreference.SharedPref;
import com.frixty.frixtyhotel.ui.LoginActivity;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    //Server Api Calls
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIEndpoints apiEndpoints;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiEndpoints = ServerInstance.getInstance().create(APIEndpoints.class);
        init();
    }

    private void init() {
        hud = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                .setCancellable(true)
                .setLabel("Logging You In")
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        hud.show();
      if(SharedPref.getString(MainActivity.this, "jwtAuthToken") == null){
         //Login or sign up
          startActivity(new Intent(MainActivity.this, LoginActivity.class));
          finish();
      }
      else{
          //verify auth token
          Map<String, String> headers = new HashMap<>();
          headers.put("Content-Type","application/json");
          compositeDisposable.add(apiEndpoints.verifyAuthToken(headers,
                  new VerifyAuthTokenBody(SharedPref.getString(MainActivity.this,"jwtAuthToken")))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<Response<MessageBodyResponse>>(){

                        @Override
                        public void onNext(@NonNull Response<MessageBodyResponse> messageBodyResponse) {
                            try {
                                if(messageBodyResponse.code() == 202){
                                    //to home screen
                                    hud.dismiss();
                                    Toast.makeText(MainActivity.this, ""+messageBodyResponse.body().getMessage(), Toast.LENGTH_SHORT).show();


                                }
                                else if (messageBodyResponse.code() == 401){
                                    //Verify Auth Failed
                                    hud.dismiss();
                                    Toast.makeText(MainActivity.this, ""+messageBodyResponse.errorBody().string(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    finish();
                                }

                            }catch (Exception e){
                                hud.dismiss();
                                e.printStackTrace();
                            }

                        }



                        @Override
                        public void onError(@NonNull Throwable e) {
                            hud.dismiss();
                            Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onComplete() {
                            hud.dismiss();
                            Log.e("Verify Auth","Auth verify complete");
                        }
                    }));
      }
    }
}