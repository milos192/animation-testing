package com.example.anax.animationtesting.fragments

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FlingAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.example.anax.animationtesting.R
import kotlinx.android.synthetic.main.showcase_flinging.*

/**
 * A showcase of the Fling animation where we will fling a LinearLayout full of squares back and forth.
 */
class FlingListFragment : Fragment() {

    enum class FlingType {
        WITHOUT_BOUNCE_BACK,
        WITH_BOUNCE_BACK
    }

    lateinit private var mFlingType: FlingType

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.showcase_flinging, container, false)
    }

    override fun onStart() {
        super.onStart()
        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                val animation = FlingAnimation(squares, DynamicAnimation.SCROLL_X)

                val horizontalPadding = resources.getDimension(R.dimen.spacing) * 2
                val widthOfSingleView = squares.getChildAt(0).width + horizontalPadding
                val scrollWidth = widthOfSingleView * squares.childCount - squares.width

                animation.setStartVelocity(-velocityX)
                        .setMinValue(0f)
                        .setFriction(2.2f)
                        .setMaxValue(scrollWidth)

                if (mFlingType == FlingType.WITH_BOUNCE_BACK) {
                    animation.addEndListener { _, _, _, velocity ->
                        if (Math.abs(velocity) > 500f) {
                            if (velocity < 0f) {
                                squares.pivotX = 0f
                            } else {
                                squares.pivotX = squares.width.toFloat()
                            }
                            val bounceBack = SpringAnimation(squares, DynamicAnimation.SCALE_X, 1f)
                            bounceBack.setStartVelocity(Math.abs(velocity) / squares.width)
                            bounceBack.spring.stiffness = SpringForce.STIFFNESS_MEDIUM
                            bounceBack.start()
                        }
                    }
                }

                animation.start()
                return super.onFling(e1, e2, velocityX, velocityY)
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }
        }

        val gestureDetector = GestureDetector(context, gestureListener)

        squares.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }

    companion object {
        fun newInstance(type: FlingType): FlingListFragment {
            val fragment = FlingListFragment()
            fragment.mFlingType = type

            return fragment
        }
    }
}