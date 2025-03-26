package com.project.dicodingplayground.practice_modul.widget.image_banner

import android.content.Intent
import android.widget.RemoteViewsService

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewFactory(this.applicationContext)
}