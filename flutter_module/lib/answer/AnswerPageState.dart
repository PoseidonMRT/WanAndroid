import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/native/LogUtils.dart';
import 'package:flutter_module/answer/AnswerPage.dart';
import 'package:flutter_module/answer/AnswerViewModel.dart';
import 'package:flutter_module/base/ProviderWidgetState.dart';
import 'package:flutter_module/detail/AnswerDetailPage.dart';
import 'package:flutter_module/main.dart';

class AnswerPageState extends ProviderWidgetState<AnswerPage, AnswerViewModel> {
  @override
  RouteObserver<PageRoute> getRouteObserver() {
    return routeObserver;
  }

  @override
  void onPageCreate() {
    super.onPageCreate();
    viewModel.fetchNextPageAnswerDatas();
  }

  @override
  void onPageResume() {
    super.onPageResume();
  }

  @override
  Widget buildWidget(
      BuildContext context, AnswerViewModel viewModel, Widget? child) {
    return WillPopScope(
        child: Scaffold(
          appBar: AppBar(
            title: Text(widget.title),
            automaticallyImplyLeading: false,
          ),
          body: Center(
            child: Container(
              margin: EdgeInsets.only(left: 15, right: 15, top: 5),
              child: Expanded(child: buildList(viewModel)),
            ),
          ),
        ),
        onWillPop: () async {
          SystemChannels.platform.invokeMethod<void>('SystemNavigator.pop');
          return true;
        });
  }

  Widget buildList(AnswerViewModel viewModel) {
    if (viewModel.answerDatas == null) {
      return Container();
    }
    var datas = viewModel.answerDatas!.data!.datas;
    return ListView.builder(
      itemCount: datas!.length,
      itemBuilder: (context, index) {
        return GestureDetector(
          onTap: () => {onItemClicked("${datas.elementAt(index).link}")},
          child: Container(
            margin: EdgeInsets.only(top: 5, bottom: 5),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "${datas.elementAt(index).title}",
                  style: TextStyle(
                    fontSize: 18,
                    color: Colors.black,
                  ),
                  textAlign: TextAlign.left,
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(
                      "作者：${datas.elementAt(index).author}",
                      style: TextStyle(
                          fontSize: 15,
                          color: Colors.grey,
                          fontStyle: FontStyle.normal),
                      textAlign: TextAlign.left,
                    ),
                    Text(
                      "分类：${datas.elementAt(index).superChapterName}/${datas.elementAt(index).chapterName}",
                      style: TextStyle(
                          fontSize: 15,
                          color: Colors.grey,
                          fontStyle: FontStyle.normal),
                      textAlign: TextAlign.left,
                    ),
                  ],
                )
              ],
            ),
          ),
        );
      },
    );
  }

  void onItemClicked(String url) {
    Navigator.push(
      context,
      new MaterialPageRoute(
          builder: (context) => new AnswerDetailPage(url: url)),
    );
  }

  @override
  AnswerViewModel createViewModel() {
    return AnswerViewModel();
  }
}
