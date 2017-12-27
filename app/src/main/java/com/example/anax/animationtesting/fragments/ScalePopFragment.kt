package com.example.anax.animationtesting.fragments

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.FloatPropertyCompat
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anax.animationtesting.R
import kotlinx.android.synthetic.main.showcase_scale_pop.*

/**
 * A showcase of a Custom Property animation, where we change the scale of all the children views
 * uniformly.
 */
class ScalePopFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.showcase_scale_pop, container, false)
    }

    override fun onStart() {
        super.onStart()

        val customProperty = object : FloatPropertyCompat<ViewGroup>("uniformScale") {
            override fun setValue(view: ViewGroup?, value: Float) {
                for (i in 0 until (view?.childCount ?: 0)) {
                    view?.getChildAt(i)?.scaleX = value
                    view?.getChildAt(i)?.scaleY = value
                }
            }

            override fun getValue(view: ViewGroup?): Float {
                return view?.getChildAt(0)?.scaleX ?: 1f
            }
        }

        scale_button.setOnClickListener {
            val scaleDown = SpringAnimation(scale_button, DynamicAnimation.SCALE_X, 0f)
            scaleDown.setMinValue(0f)

            scaleDown.addEndListener { _, canceled, value, velocity ->
                scale_teammates_list.visibility = View.VISIBLE

                val animation = SpringAnimation(scale_teammates_list, customProperty, 1f)
                animation.setStartValue(0f)
                animation.spring.stiffness = SpringForce.STIFFNESS_LOW
                animation.spring.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                animation.minimumVisibleChange = DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE

                animation.start()
            }

            scaleDown.start()
        }
    }
}