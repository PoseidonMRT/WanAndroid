import 'package:flutter_module/native/LogUtils.dart';
import 'package:flutter_module/base/BaseModel.dart';
import 'package:flutter_module/base/DioClient.dart';
import 'package:flutter_module/entities/AnswerDataList.dart';

class AnswerModel extends BaseModel {
  int pageIndex = 0;

  AnswerModel(DioClient dioClient) : super(dioClient);

  Future<AnswerDataList> fetchAnswerDatasByPage() async {
    String path = "/wenda/list/${pageIndex}/json";
    var response = await dioClient.get(path);
    AnswerDataList answerDataList = AnswerDataList.fromJson(response.data);
    return answerDataList;
  }
}
