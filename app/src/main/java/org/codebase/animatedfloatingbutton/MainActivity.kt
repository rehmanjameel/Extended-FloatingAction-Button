package org.codebase.animatedfloatingbutton

import android.graphics.Path.Direction
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlin.math.max


class MainActivity : AppCompatActivity(), View.OnTouchListener {
    lateinit var fab: ExtendedFloatingActionButton

    private val CLICK_DRAG_TOLERANCE =
        10f // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.


    private var downRawX = 0f
    private  var downRawY:kotlin.Float = 0f
    private var dX = 0f
    private  var dY:kotlin.Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view: View = findViewById(R.id.fab);
        view.setOnTouchListener(this);

        fab = findViewById(R.id.fab)

        fab.shrink()

//        fab.setOnClickListener {
//            if (!fab.isExtended) {
//                fab.extend()
//                fab.text = "Boost: Off \nPing: 99 Jitter: 15"
//                fab.maxLines = 2
//                fab.background = applicationContext.getDrawable(R.drawable.fab_back)
//            } else {
//                fab.shrink()
//                fab.background = applicationContext.getDrawable(R.drawable.circle_fab)
//
//            }
//        }
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {

        val action = motionEvent.action
        return if (action == MotionEvent.ACTION_DOWN) {
            downRawX = motionEvent.rawX
            downRawY = motionEvent.rawY
            dX = view!!.x - downRawX
            dY = view!!.y - downRawY
            true // Consumed
        } else if (action == MotionEvent.ACTION_MOVE) {
            val viewWidth = view!!.width
            val viewHeight = view!!.height
            val viewParent = view!!.parent as View
            val parentWidth = viewParent.width
            val parentHeight = viewParent.height
            var newX = motionEvent.rawX + dX

            newX = max(0f, newX) // Don't allow the FAB past the left hand side of the parent

            if (newX < 1.0) {
                fab.iconGravity = MaterialButton.ICON_GRAVITY_START
//                fab.layoutDirection =

            } else {
                fab.iconGravity = MaterialButton.ICON_GRAVITY_END
            }
            newX = Math.min(
                (parentWidth - viewWidth).toFloat(),
                newX) // Don't allow the FAB past the right hand side of the parent

            Log.e("error", newX.toString())

            var newY = motionEvent.rawY + dY
            newY = Math.max(0f, newY) // Don't allow the FAB past the top of the parent
            newY = Math.min(
                (parentHeight - viewHeight).toFloat(),
                newY
            ) // Don't allow the FAB past the bottom of the parent
            view!!.animate()
                .x(newX)
                .y(newY)
                .setDuration(0)
                .start()
            true // Consumed
        } else if (action == MotionEvent.ACTION_UP) {
            val upRawX = motionEvent.rawX
            val upRawY = motionEvent.rawY
            val upDX = upRawX - downRawX
            val upDY = upRawY - downRawY
            if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                performClick()
            } else { // A drag
                true // Consumed
            }
        } else {
            super.onTouchEvent(motionEvent)
        }
    }

    private fun performClick(): Boolean {

        if (!fab.isExtended) {
            fab.extend()
            fab.text = "Boost: Off \nPing: 99 Jitter: 15"
            fab.maxLines = 2
            fab.background = applicationContext.getDrawable(R.drawable.fab_back)
        } else {
            fab.shrink()
            fab.background = applicationContext.getDrawable(R.drawable.circle_fab)

        }
        return true
    }
}