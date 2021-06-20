import 'package:dio/dio.dart';
import 'package:flutter_module/base/DioClient.dart';

///
/// BaseModel class is the parent of all Model class
///
abstract class BaseModel {
  late Dio dioClient;

  BaseModel(DioClient dioClient) {
    this.dioClient = dioClient.init();
  }
}
