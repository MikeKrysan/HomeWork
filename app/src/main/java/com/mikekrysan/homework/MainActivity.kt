package com.mikekrysan.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


/**
 * 24.1 Добавляем Recycler View
 * 24.2 В MainActivity создаем список
 * 24.3 Создаем верстку под элемент RV который будет представлять фильм: film_item.xml
 * 24.4 Создаем ViewHolder FilmViewHolder
 * 24.5 Создадим класс RV адаптера: FilmListRecyclerAdapter
 * 24.6 В классе FilmViewHolder делаем привязку полей объекта Film к View  в нашей верстке
 * 24.7 В FilmListRecyclerAdapter реализовываем логику привязки фильми из нашей базы данных во ViewHolder
 * 24.8 В MainActivity создадим поле для нашего адаптера на уровне класса
 * 24.9 Для отступов между элементами создадим класс TopSpacingItemDecoration
 * 24.10 Инициализируем RV в методе onCreate класса MainActivity
 * 24.11 создадим новое активити: DetailsActivity и заполним верстку activity_details.xml
 * 24.12 Доделаем обработку нажатий по элементам в RV, в методе click() и в адаптере onBindViewHolder() класса FilmListRecyclerAdapter
 * 24.13 Чтобы передавать объект в активити чтобы при откритии было видно постер, название фильма и описание, необходимо дата классу Film добавить аннотоцию @Parcelize
 *      и наследовать интерфейс Parcelable
 * 24.14 Теперь мы можем наш объект передать на другое активити, для этого его нужно положить в бандл, прикрепить к интенту а интент уже запустит новое активити
 * 24.15 теперь в нашем DetailsActivity нужно эту посылку получить
 * 24.16 Осталось заполнить наши View в DetailsActivity из полученного объекта
 */

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()

        //Запускаем фрагмент при старте
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    fun launchDetailsFragment(film: Film) {
        //Создаем "посылку"
        val bundle = Bundle()
        //Кладем наш фильм в "посылку"
        bundle.putParcelable("film", film)
        //Кладем фрагмент с деталями в переменную
        val fragment = DetailsFragment()
        //Прикрепляем нашу "посылку" к фрагменту
        fragment.arguments = bundle

        //Запускаем фрагмент
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun initNavigation() {

        bottom_navigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}