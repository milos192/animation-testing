package com.example.anax.animationtesting.fragments

import android.os.Bundle
import android.os.Handler
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anax.animationtesting.R
import kotlinx.android.synthetic.main.showcase_target_value_change.*

class TargetValueChangeFragment : Fragment() {

    enum class TargetValueAnimationType {
        STANDARD,
        PHYSICS_BASED
    }

    private lateinit var mType: TargetValueAnimationType

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.showcase_target_value_change, container, false)
    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed({
            if (mType == TargetValueAnimationType.PHYSICS_BASED) {
                animateWithPhysics()
            } else {
                animateWithoutPhysics()
            }
        }, 500)
    }

    private fun animateWithoutPhysics() {
        val animation = subject.animate()
                .x(target.x)
                .y(target.y)
                .setDuration(500)

        animation.start()

        Handler().postDelayed({
            animation.cancel()

            val padding = resources.getDimension(R.dimen.spacing)

            target.x = getNewX(padding)
            target.y = getNewY(padding)

            subject.animate()
                    .x(target.x)
                    .y(target.y)
                    .setDuration(200)
                    .start()
        }, 300)
    }

    private fun animateWithPhysics() {
        val animationX = createSpringAnimation(subject, DynamicAnimation.X, target.x)
        val animationY = createSpringAnimation(subject, DynamicAnimation.Y, target.y)

        animationX.start()
        animationY.start()

        Handler().postDelayed({
            val padding = resources.getDimension(R.dimen.spacing)

            target.x = getNewX(padding)
            target.y = getNewY(padding)

            animationX.animateToFinalPosition(target.x)
            animationY.animateToFinalPosition(target.y)
        }, 150)
    }

    private fun createSpringAnimation(view: View, property: DynamicAnimation.ViewProperty, endValue: Float): SpringAnimation {
        val springAnimation = SpringAnimation(view, property, endValue)
        springAnimation.spring.stiffness = SpringForce.STIFFNESS_LOW
        springAnimation.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY

        return springAnimation
    }

    private fun getNewX(padding: Float) = padding * 3

    private fun getNewY(padding: Float) =
            target_value_change_showcase_layout.height / 1.2f - subject.height - padding

    companion object {
        fun newInstance(type: TargetValueAnimationType): TargetValueChangeFragment {
            val fragment = TargetValueChangeFragment()
            fragment.mType = type

            return fragment
        }
    }
}