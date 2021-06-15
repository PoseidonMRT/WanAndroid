import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class AnswerPage extends StatefulWidget {
  AnswerPage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  _AnswerPageState createState() => _AnswerPageState();
}

class _AnswerPageState extends State<AnswerPage> {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        child: Scaffold(
          appBar: AppBar(
            title: Text(widget.title),
          ),
          body: Center(
            child: Text("Answer Page"),
          ),
        ),
        onWillPop: () async {
          SystemChannels.platform.invokeMethod<void>('SystemNavigator.pop');
          return true;
        });
  }
}
