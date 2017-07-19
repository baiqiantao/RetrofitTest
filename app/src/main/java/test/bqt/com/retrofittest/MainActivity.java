package test.bqt.com.retrofittest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

	private static final String BASE_URL = "http://apis.baidu.com";
	private static final String API_KEY = "";
	private static final String NUMBER = "18680536603";

	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		textView = new TextView(this);
		setContentView(textView);
		Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl(BASE_URL).build();
		//Converter.Factory用于对象转化，本例因为服务器返回的是json格式的数组，所以这里设置了GsonConverterFactory完成对象的转化
		IPhoneService service = retrofit.create(IPhoneService.class);
		Call<PhoneResult> call = service.getResult(API_KEY, NUMBER);
		call.enqueue(new Callback<PhoneResult>() {//异步请求
			@Override
			public void onResponse(Call<PhoneResult> call, Response<PhoneResult> response) {
				if (response.isSuccessful()) {
					PhoneResult result = response.body();
					if (result != null) {
						textView.setText(result.toString());
						Toast.makeText(MainActivity.this, result.retData.city, Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onFailure(Call<PhoneResult> call, Throwable t) {
				Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
			}
		});

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {//log，统一的header等你需要的配置
			@Override
			public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
				return null;
			}
		}).build();
	}
}
