package id.jyotisa.storyapp

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import id.jyotisa.storyapp.database.StoryDatabase
import java.net.URL

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()
    private val database: StoryDatabase = StoryDatabase.getDatabase(mContext)

    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        val c: Cursor = getAll()
        c.moveToFirst()
        while(!c.isAfterLast) {
            mWidgetItems.add(BitmapFactory.decodeStream(URL(c.getString(3)).openStream()))
            c.moveToNext()
        }
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageViewWidget, mWidgetItems[position])

        val extras = bundleOf(
            StoriesBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    private fun getAll(): Cursor {
        return database.storyDao().getAll()
    }
}