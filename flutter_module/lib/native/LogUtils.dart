import 'package:flutter/services.dart';

class LogUtils {
  static const String _tag = "LogUtils";
  static const _perform = const MethodChannel("flutter_android_log");

  static void d(String message) {
    _perform.invokeMethod("logD", {'tag': _tag, 'msg': message});
  }

  static void e(String message) {
    _perform.invokeMethod("logE", {'tag': _tag, 'msg': message});
  }
}
