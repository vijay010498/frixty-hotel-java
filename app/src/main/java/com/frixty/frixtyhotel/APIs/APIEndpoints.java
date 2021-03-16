package com.frixty.frixtyhotel.APIs;

import com.frixty.frixtyhotel.Models.MessageBodyResponse;
import com.frixty.frixtyhotel.APIs.RequestBody.SignInBody;
import com.frixty.frixtyhotel.APIs.RequestBody.VerifyAuthTokenBody;
import com.frixty.frixtyhotel.Models.UserModel;


import java.util.Map;

import io.reactivex.Observable;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface APIEndpoints {

    @POST("api/v1/users/signin")
    Observable<Response<UserModel>> signIn(
            @HeaderMap Map<String, String> headers,
            @Body SignInBody signInBody);

    @POST("api/v1/users/verifyauthtoken")
    Observable<Response<MessageBodyResponse>>  verifyAuthToken (@HeaderMap Map<String, String> headers,
                                                                @Body VerifyAuthTokenBody verifyAuthTokenBody);
}
