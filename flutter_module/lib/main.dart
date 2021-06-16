import 'package:flutter/material.dart';
import 'package:flutter_module/answer/AnswerPage.dart';
import 'package:flutter_module/home/MyHomePage.dart';

final RouteObserver<PageRoute> routeObserver = RouteObserver<PageRoute>();

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Demo',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        initialRoute: '/answer',
        routes: <String, WidgetBuilder>{
          '/': (BuildContext context) => MyHomePage(
                title: 'HomePage',
              ),
          '/answer': (BuildContext context) => AnswerPage(title: '问答')
        });
  }
}
