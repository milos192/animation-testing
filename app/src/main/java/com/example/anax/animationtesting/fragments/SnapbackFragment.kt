package com.example.anax.animationtesting.fragments

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.anax.animationtesting.R
import com.example.anax.animationtesting.afterMeasured
import kotlinx.android.synthetic.main.showcase_snapback.*

class SnapbackFragment : Fragment() {

    enum class AnimationType {
        TYPE_NONE,
        TYPE_STANDARD,
        TYPE_PHYSICS_BASED
    }

    private var mType = AnimationType.TYPE_NONE

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.showcase_snapback, container, false);
    }

    override fun onStart() {
        super.onStart()

        var deltaX = 0f
        var deltaY = 0f

        var originalX = 0f
        var originalY = 0f

        image.afterMeasured {
            originalX = image.x
            originalY = image.y
        }

        image.isClickable = true
        image.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    deltaX = image.x - motionEvent.rawX
                    deltaY = image.y - motionEvent.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    image.x = motionEvent.rawX + deltaX
                    image.y = motionEvent.rawY + deltaY
                }
                MotionEvent.ACTION_UP -> {
                    if (mType == AnimationType.TYPE_PHYSICS_BASED) {
                        val springAnimX = createSpringAnimation(image, DynamicAnimation.X, originalX)
                        val springAnimY = createSpringAnimation(image, DynamicAnimation.Y, originalY)

                        springAnimX.start()
                        springAnimY.start()
                    } else {
                        image.animate().x(originalX).y(originalY).setDuration(300).start()
                    }
                }
            }

            false
        }
    }

    private fun createSpringAnimation(view: View, property: DynamicAnimation.ViewProperty, endValue: Float): SpringAnimation {
        val springAnimation = SpringAnimation(view, property, endValue)
        springAnimation.spring.stiffness = SpringForce.STIFFNESS_LOW
        springAnimation.spring.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY

        return springAnimation
    }

    companion object {
        fun newInstance(type: AnimationType): SnapbackFragment {
            val snapBack = SnapbackFragment()
            snapBack.mType = type

            return snapBack
        }
    }
}