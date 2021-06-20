import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/answer/AnswerPage.dart';
import 'package:flutter_module/answer/AnswerViewModel.dart';
import 'package:flutter_module/base/ProviderWidgetState.dart';
import 'package:flutter_module/main.dart';

class AnswerPageState extends ProviderWidgetState<AnswerPage, AnswerViewModel> {
  @override
  RouteObserver<PageRoute> getRouteObserver() {
    return routeObserver;
  }

  @override
  Widget buildWidget(
      BuildContext context, AnswerViewModel viewModel, Widget? child) {
    return WillPopScope(
        child: Scaffold(
          appBar: AppBar(
            title: Text(widget.title),
          ),
          body: Center(
            child: Column(
              children: [
                Row(
                  children: [
                    Text("Count:${viewModel.count}"),
                    Spacer(),
                    GestureDetector(
                      onTap: () => viewModel.fetchNextPageAnswerDatas(),
                      child: Text(
                        "click to increment count",
                      ),
                    )
                  ],
                ),
                Expanded(child: buildList(viewModel))
              ],
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
        return Text("${datas.elementAt(index).title}");
      },
    );
  }

  @override
  AnswerViewModel createViewModel() {
    return AnswerViewModel();
  }
}
