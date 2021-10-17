import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/answer/AnswerPage.dart';
import 'package:flutter_module/home/MyHomePage.dart';
import 'package:flutter_module/utils/Constants.dart';

final RouteObserver<PageRoute> routeObserver = RouteObserver<PageRoute>();

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  // This widget is the root of your application.
  @override
  State<StatefulWidget> createState() {
    return _MyAppState();
  }
}

class _MyAppState extends State<MyApp> {
  MethodChannel _methodChannel = MethodChannel(Constants.channelName);
  Widget _initHome = MyHomePage(
    title: 'HomePage',
  );

  @override
  void initState() {
    super.initState();
    _methodChannel.setMethodCallHandler((call) async {
      print("method:" + call.method);
      switch (call.method) {
        case Constants.navigatorPush:
          var route = call.arguments[Constants.navigatorParamsRoutes];
          initRoute(route);
          break;
        default:
          initRoute(Constants.pathOfHome);
          break;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: _initHome);
  }

  void initRoute(String route) {
    setState(() {
      if (Constants.pathOfHome == route) {
        _initHome = MyHomePage(title: "HomePage");
      }
      if (Constants.pathOfAnswer == route) {
        _initHome = AnswerPage(title: "问答");
      }
    });
  }
}
