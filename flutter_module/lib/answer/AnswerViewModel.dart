import 'package:flutter_module/answer/AnswerModel.dart';
import 'package:flutter_module/base/BaseViewModel.dart';
import 'package:flutter_module/base/DioClient.dart';
import 'package:flutter_module/entities/AnswerDataList.dart';

class AnswerViewModel extends BaseViewModel<AnswerModel> {
  int count = 0;

  AnswerDataList? answerDatas = null;

  void fetchNextPageAnswerDatas() {
    model.fetchAnswerDatasByPage().then((value) {
      answerDatas = value;
      count++;
      notifyListeners();
    });
  }

  @override
  AnswerModel createModel() {
    return AnswerModel(DioClient());
  }
}
