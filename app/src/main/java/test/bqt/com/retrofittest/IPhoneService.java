package test.bqt.com.retrofittest;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IPhoneService {
	//每一个方法必须要有一个HTTP注解来标明请求的方式和【相对URL】；有五种内置的注解方式：GET、POST、PUT、DELETE以及HEAD
	//通过@GET注解标识为get请求，@GET中所填写的value和baseUrl组成完整的路径，baseUrl在构造retrofit对象时给出，相对URL需要在注解里面明确给出
	@GET("group/{id}/users")
	//一个请求的URL可以通过替换块和请求方法的参数来动态的更新，替换块是由被{}包裹起来的字符串构成的，相应的方法参数需要由@Path来注解同样的字符串
	//这里你可以把{id}当做占位符，而实际运行中会通过@PATH("id")所标注的参数进行替换
	Call<List<PhoneResult>> groupList(@Path("id") int groupId);

	//注解@Query用于一般的传参，当然你也可以将query参数直接写在@GET注解中的相对URL里
	@GET("group/{id}/users")
	Call<List<PhoneResult>> groupList(@Path("id") int groupId, @Query("sort") String sort);

	//复杂的query参数可以用Map来构建
	@GET("group/{id}/users")
	Call<List<PhoneResult>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);

	//能够通过@Body注解来指定一个方法作为HTTP请求主体
	@POST("users/new")
	Call<PhoneResult> createUser(@Body PhoneResult result);

	//发送form-encoded的数据（即：以表单的方式传递简单的键值对）。每个键值对需要用@Filed来注解键名，随后的对象需要提供值
	@FormUrlEncoded
	@POST("user/edit")
	Call<PhoneResult> updateUser(@Field("first_name") String first, @Field("last_name") String last);

	//也可以通过@Multipart注解方法来发送Mutipart请求（多文件上传）。每个部分需要使用@Part来注解。
	@Multipart
	@PUT("user/photo")
	Call<PhoneResult> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

	//@PartMap用于标识一个Map，Map的key为String类型，代表上传的键值对的key，value即为RequestBody，有点类似@Part的封装版本。
	//可以在Map中put进一个或多个文件，键值对等，当然你也可以分开，单独的键值对也可以使用@Part
	@Multipart
	@POST("register")
	Call<PhoneResult> registerUser(@PartMap Map<String, RequestBody> params, @Part("password") RequestBody password);

	//可以通过使用@Headers注解来设置请求静态头。
	@Headers("Cache-Control: max-age=640000")
	@GET("widget/list")
	Call<List<PhoneResult>> widgetList();

	//注意的是头部参数并不会相互覆盖，同一个名称的所有头参数都会被包含进请求里面。
	@Headers({"Accept: application/vnd.github.v3.full+json", "User-Agent: Retrofit-Sample-App"})
	@GET("users/{username}")
	Call<PhoneResult> getUser(@Path("username") String username);

	//当然你可以通过 @Header 注解来动态更新请求头。一个相应的参数必须提供给 @Header 注解。
	//如果这个值是空（null）的话，那么这个头部参数就会被忽略。否则的话， 值的 toString 方法将会被调用，并且使用调用结果。
	@GET("user")
	Call<PhoneResult> getUser2(@Header("Authorization") String authorization);

	//真实案例中使用到的方法
	@GET("/apistore/mobilenumber/mobilenumber")
	Call<PhoneResult> getResult(@Header("apikey") String apikey, @Query("phone") String phone);
}