package com.mikekrysan.homework

import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import java.util.concurrent.Executors
import kotlin.math.hypot
import kotlin.math.roundToInt

object AnimationHelper {
    //Это переменная для того, чтобы круг проявления расходился именно от иконки меню навигации
    private const val menuItems = 4

    //В метод приходит 3 параметра: rootView которое одновременно является и контейнером; активити для того чтобы вернуть выполнение нового треда в UI поток; позиция в меню навигации, чтоб круг проявления расходился от иконки меню навигации
    fun perfomFragmentCircularRevealAnimation(rootView: View, activity: Activity, position: Int) {
        //создаем новый тред:
        Executors.newSingleThreadExecutor().execute {
            //В бесконечном цикле проверяем, когда наше анимированное view будет "прикреплено" к экрану
            while (true) {
                //когда оно будте прикреплено, выполним код:
                if (rootView.isAttachedToWindow) {
                    //возвращаемся в главный тред, чтобы выполнить анимацию
                    activity.runOnUiThread {
                        //математика вычисления старта анимации
                        val itemCenter = rootView.width / (menuItems * 2)
                        val step = (itemCenter * 2) * (position - 1) + itemCenter
                        val x: Int = step
                        val y: Int = rootView.y.roundToInt() + rootView.height
                        val startRadius = 0
                        val endRadius = hypot(rootView.width.toDouble(), rootView.height.toDouble())
                        //создаем саму анимацию
                        ViewAnimationUtils.createCircularReveal(rootView, x, y, startRadius.toFloat(), endRadius.toFloat()).apply {
                            //устанавливаем время анимации
                            duration = 500
                            //интерполятор для более естественной анимации
                            interpolator = AccelerateDecelerateInterpolator()
                            //Запускаем
                            start()
                        }
                        //Выставляем видимость нашего элемента
                        rootView.visibility = View.VISIBLE
                    }
                    return@execute
                }
            }
        }
    }
}
