package com.hala.http;


import com.hala.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by kiddo on 2017/11/29.
 */

public interface HttpRequest {

    @GET("work/GetPasterInfoForAndroid")
    Observable<BaseBean> getARFaceData(
    );




}
