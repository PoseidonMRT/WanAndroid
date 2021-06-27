import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/detail/AnswerDetailPage.dart';
import 'package:webview_flutter/webview_flutter.dart';

class AnswerDetailPageState extends State<AnswerDetailPage> {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        child: Scaffold(
          appBar: AppBar(
            title: Text("问答"),
          ),
          body: Center(
            child: WebView(
              initialUrl: widget.url,
              javascriptMode: JavascriptMode.unrestricted,
            ),
          ),
        ),
        onWillPop: () async {
          Navigator.of(context).pop();
          return true;
        });
  }
}
