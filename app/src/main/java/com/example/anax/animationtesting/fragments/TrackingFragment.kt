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
import kotlinx.android.synthetic.main.showcase_tracking.*

class TrackingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.showcase_tracking, container, false)
    }

    override fun onStart() {
        super.onStart()

        var deltaX: Float
        var deltaY: Float

        var lastX = 0f
        var lastY = 0f

        var beginTranslationX = 0f
        var beginTranslationY = 0f

        val trackingAnimationX = createSpringAnimation(follower, DynamicAnimation.TRANSLATION_X, 0f)
        val trackingAnimationY = createSpringAnimation(follower, DynamicAnimation.TRANSLATION_Y, 0f)

        lead.isClickable = true
        lead.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    lastX = motionEvent.rawX
                    lastY = motionEvent.rawY
                    beginTranslationX = lead.translationX
                    beginTranslationY = lead.translationY
                }
                MotionEvent.ACTION_MOVE -> {
                    deltaX = motionEvent.rawX - lastX
                    deltaY = motionEvent.rawY - lastY

                    lead.translationX = beginTranslationX + deltaX
                    lead.translationY = beginTranslationY + deltaY

                    trackingAnimationX.animateToFinalPosition(lead.translationX)
                    trackingAnimationY.animateToFinalPosition(lead.translationY)
                }
            }

            false
        }
    }

    private fun createSpringAnimation(view: View, property: DynamicAnimation.ViewProperty, finalPosition: Float): SpringAnimation {
        val animation = SpringAnimation(view, property, finalPosition)
        animation.spring.stiffness = SpringForce.STIFFNESS_LOW
        animation.spring.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY

        return animation
    }
}