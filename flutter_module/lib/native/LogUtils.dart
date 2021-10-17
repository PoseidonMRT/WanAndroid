import 'package:flutter/services.dart';

class LogUtils {
  static const String _tag = "LogUtils";
  static const _perform = const MethodChannel("flutter_android_log");

  static void i(String message) {
    _perform.invokeMethod("logI", {'tag': _tag, 'msg': message});
  }

  static void v(String message) {
    _perform.invokeMethod("logV", {'tag': _tag, 'msg': message});
  }

  static void d(String message) {
    _perform.invokeMethod("logD", {'tag': _tag, 'msg': message});
  }

  static void e(String message) {
    _perform.invokeMethod("logE", {'tag': _tag, 'msg': message});
  }
}
