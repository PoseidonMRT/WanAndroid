import 'package:flutter/cupertino.dart';

///
/// LifecycleAwareState is a class which extends from state
/// in this class, we give the concept of Widget Life cycle
/// just like View life cycle in Android system
/// The functions in this class are compared with the lifecycle functions in Android system as follows
// ----------------------------------------
// | Android system  | LifecycleAwareState|
// ----------------------------------------
// | onCreate        | onPageCreate       |
// ----------------------------------------
// | onResume        | onPageResume       |
// ----------------------------------------
// | onStart         | onPageStart        |
// ----------------------------------------
// | onStop          | onPageStop         |
// ----------------------------------------
///
abstract class LifecycleAwareState<T extends StatefulWidget> extends State<T>
    with RouteAware, WidgetsBindingObserver {
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance!.addObserver(this);
    onPageCreate();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    ModalRoute<dynamic>? modalRoute = ModalRoute.of(context);
    if (modalRoute is PageRoute) {
      getRouteObserver().subscribe(this, modalRoute);
    }
  }

  @override
  void didPopNext() {
    super.didPopNext();
    onPageResume();
  }

  @override
  void didPush() {
    super.didPush();
    onPageStart();
  }

  @override
  void didPushNext() {
    super.didPushNext();
    onPagePaused();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed) {
      onPageResume();
    } else if (state == AppLifecycleState.paused) {
      onPagePaused();
    }
    super.didChangeAppLifecycleState(state);
  }

  @override
  void dispose() {
    WidgetsBinding.instance!.removeObserver(this);
    getRouteObserver().unsubscribe(this);
    super.dispose();
  }

  RouteObserver<PageRoute> getRouteObserver();

  void onPageCreate() {}

  void onPageStart() {}

  void onPageResume() {}

  void onPagePaused() {}
}
