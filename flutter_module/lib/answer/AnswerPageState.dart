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
            child: Row(
              children: [
                Text("Count:${viewModel.count}"),
                Spacer(),
                GestureDetector(
                  onTap: () => viewModel.incrementCount(),
                  child: Text(
                    "click to increment count",
                  ),
                )
              ],
            ),
          ),
        ),
        onWillPop: () async {
          SystemChannels.platform.invokeMethod<void>('SystemNavigator.pop');
          return true;
        });
  }

  @override
  AnswerViewModel createViewModel() {
    return AnswerViewModel();
  }
}
