package com.karen.curvedbottomnavigationview

import android.content.res.Resources

/** Converting INT to DP*/
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

/** Converting FLOAT to DP*/
val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()