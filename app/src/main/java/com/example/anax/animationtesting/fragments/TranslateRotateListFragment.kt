package com.example.anax.animationtesting.fragments

import android.os.Bundle
import android.support.animation.DynamicAnimation
import android.support.animation.SpringAnimation
import android.support.animation.SpringForce
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anax.animationtesting.R
import kotlinx.android.synthetic.main.showcase_translation_with_rotation.*

class TranslateRotateListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.showcase_translation_with_rotation, container, false)
    }

    override fun onStart() {
        super.onStart()

        teammates_button.setOnClickListener({
            val scaleDisappear = SpringAnimation(teammates_button, DynamicAnimation.SCALE_Y, 0f)
            scaleDisappear.spring.stiffness = SpringForce.STIFFNESS_HIGH
            scaleDisappear.addEndListener({ _, _, _, _ ->
                teammates_button.visibility = View.GONE
                scale_teammates_list.visibility = View.VISIBLE
                animate()
            })
            scaleDisappear.start()
        })
    }

    private fun animate() {
        val springAnimation = SpringAnimation(scale_teammates_list, DynamicAnimation.TRANSLATION_X, 0f)
        springAnimation.setStartValue(300f)
        springAnimation.spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        springAnimation.spring.stiffness = SpringForce.STIFFNESS_MEDIUM
        springAnimation.start()

        val spring = SpringForce(0f)
        spring.dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
        spring.stiffness = SpringForce.STIFFNESS_VERY_LOW

        for (i in 0 until scale_teammates_list.childCount) {
            val view = scale_teammates_list.getChildAt(i)
            val rotation = SpringAnimation(view, DynamicAnimation.ROTATION)
            rotation.spring = spring
            rotation.setStartValue(10f)
            rotation.start()
        }
    }
}