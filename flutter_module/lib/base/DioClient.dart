import 'package:dio/dio.dart';

class DioClient {
  Dio init() {
    Dio _dio = Dio();
    _dio.options.baseUrl = "https://www.wanandroid.com";
    _dio.options.responseType = ResponseType.json;
    return _dio;
  }
}

class ApiInterceptor extends Interceptor {
  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {}

  @override
  void onError(DioError err, ErrorInterceptorHandler handler) {}

  @override
  void onResponse(Response response, ResponseInterceptorHandler handler) {}
}
