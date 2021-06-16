import 'package:flutter_module/base/BaseModel.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';

///
/// ViewModel class which extends from ChangeNotifier class
///
/// this class will maintain an object which implements BaseModel class
///
abstract class BaseViewModel<T extends BaseModel> extends ChangeNotifier {
  late T model;

  bool loading = false;

  bool isLoadingShowing = false;

  BaseViewModel() {
    model = createModel();
  }

  T createModel();
}
