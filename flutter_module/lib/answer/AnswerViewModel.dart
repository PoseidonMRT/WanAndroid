import 'package:flutter_module/answer/AnswerModel.dart';
import 'package:flutter_module/base/BaseViewModel.dart';

class AnswerViewModel extends BaseViewModel<AnswerModel> {
  int count = 0;

  void incrementCount() {
    count++;
    notifyListeners();
  }

  @override
  AnswerModel createModel() {
    return AnswerModel();
  }
}
