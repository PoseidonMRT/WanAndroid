package com.poseidon.wanandroid

import android.app.Application
import android.util.Log
import com.poseidon.wanandroid.utils.Constants
import dagger.hilt.android.HiltAndroidApp
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

@HiltAndroidApp
class WanAndroidApplication : Application() {
    companion object {
        private const val channelName = "flutterChannel";
        private const val navigatorPush = "navigator_push";
        private const val navigatorParamsRoutes = "navigator_routes";
        private lateinit var flutterEngine: FlutterEngine

        fun setFlutterInitRoute(path: String) {
            var methodChannel = MethodChannel(flutterEngine.dartExecutor, channelName)
            val params = mapOf(
                navigatorParamsRoutes to path
            )
            methodChannel.invokeMethod(navigatorPush, params)
        }
    }

    override fun onCreate() {
        super.onCreate()
        preCreateFlutterEngine()
    }

    private fun preCreateFlutterEngine() {
        flutterEngine = FlutterEngine(this)
        flutterEngine.navigationChannel.setInitialRoute(Constants.pathOfAnswer)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        FlutterEngineCache.getInstance().put(Constants.nameOfAnswerEngineCache, flutterEngine)
    }
}