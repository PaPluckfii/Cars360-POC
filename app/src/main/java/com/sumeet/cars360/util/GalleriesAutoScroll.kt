package com.sumeet.cars360.util

import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import com.sumeet.cars360.ui.customer.util.GalleriesRecyclerAdapter

class GalleriesAutoScroll {

    var speedScroll = 0L
    val handler = Handler()

    var count = 0
    var flag = true
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: GalleriesRecyclerAdapter

    val runnable = object : Runnable {

        override fun run() {

            if (count < adapter.itemCount) {
                if (count == adapter.itemCount - 1) {
                    flag = false;
                } else if (count == 0) {
                    flag = true;
                }
                if (flag) count++;
                else count--;

                recyclerView.smoothScrollToPosition(count);
                handler.postDelayed(this, speedScroll);
            }
        }
    }

    fun autoScroll(speed: Long){
        speedScroll = speed
        handler.postDelayed(runnable,speedScroll)
    }

}