package com.frixty.frixtyhotel.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frixty.frixtyhotel.APIs.APIEndpoints;
import com.frixty.frixtyhotel.APIs.RequestBody.SignInBody;
import com.frixty.frixtyhotel.APIs.ServerInstance;
import com.frixty.frixtyhotel.MainActivity;
import com.frixty.frixtyhotel.Models.UserModel;
import com.frixty.frixtyhotel.R;
import com.frixty.frixtyhotel.sharedPreference.SharedPref;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_email)
    TextInputEditText edit_email;
    @BindView(R.id.edit_password)
    TextInputEditText edit_password;
    @BindView(R.id.image_close)
    ImageView image_close;
    @BindView(R.id.button_login)
    MaterialButton button_login;
    private KProgressHUD hud;


    //Server Api Calls
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIEndpoints apiEndpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiEndpoints = ServerInstance.getInstance().create(APIEndpoints.class);
        ButterKnife.bind(this);
        init();
        setListener();
    }
    private void init() {
        hud = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark))
                .setCancellable(true)
                .setLabel("Logging You In")
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);



    }

    private void setListener() {
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(edit_email.getText().toString().trim()) || !isValidMail(edit_email.getText().toString().trim()) ){
                    edit_email.setError("Email Empty or not valid");
                    return;
                }
                else if(TextUtils.isEmpty(edit_password.getText().toString())){

                    edit_password.setError("Password Empty");
                    return;
                }
                hud.show();


                //call api to Login in
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json");

                compositeDisposable.add(apiEndpoints.signIn(headers, new SignInBody(edit_email.getText().toString(),
                        edit_password.getText().toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Response<UserModel>>(){
                            @Override
                            public void onNext(@NonNull Response<UserModel> userModelResponse) {

                                try {
                                    if(userModelResponse.code() == 200){
                                        //Login Success save the jwtAuth and move to next screen
                                        Toast.makeText(LoginActivity.this, ""+userModelResponse.body().getAuth().getJwtAuthToken(), Toast.LENGTH_SHORT).show();
                                        SharedPref.putString(LoginActivity.this,"jwtAuthToken",userModelResponse.body().getAuth().getJwtAuthToken());
                                        goToMainActivity();
                                    }
                                    else if(userModelResponse.code() == 400){
                                        //Error update the UI
                                        //Toast.makeText(LoginActivity.this, ""+userModelResponse.errorBody().string(), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(LoginActivity.this, "Email or password Incorrect", Toast.LENGTH_SHORT).show();
                                        edit_password.setError("Password Invalid");
                                        edit_email.setError("Email Invalid");

                                    }

                                }catch (Exception e){
                                    hud.dismiss();
                                    e.printStackTrace();
                                }
                            }

                            private void goToMainActivity() {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                hud.dismiss();
                                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {
                                hud.dismiss();
                                Log.e("Login","Login Complete");

                            }
                        }));

                /*compositeDisposable.add(apiEndpoints.signIn(headers, new SignInBody(edit_email.getText().toString().trim()
                ,edit_password.getText().toString().trim()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Response<UserModel>>() {
                            @Override
                            public void accept(Response<UserModel> userModelResponse) throws Exception {
                                hud.dismiss();
                                Toast.makeText(LoginActivity.this, ""+userModelResponse.code(), Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(LoginActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));*/


            }
        });

    }



    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
          MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(LoginActivity.this)
                .setTitle("Cancel Process?")
                .setMessage("Are you sure want to cancel the registration process?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}