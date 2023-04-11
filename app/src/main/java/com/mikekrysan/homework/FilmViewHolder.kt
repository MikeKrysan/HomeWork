package com.mikekrysan.homework

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.film_item.*
import kotlinx.android.synthetic.main.film_item.view.*

class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    //Привязываем View из layout к переменным
    private val title = itemView.title
    private val poster = itemView.poster
    private val description = itemView.description
    //добавляем логику внедрения рейтинга в верстку. Находим в верстке наш прогресс-бар для рейтинга:
    private val ratingDonut = itemView.rating_donut


    //В этом методе кладем данные из Film в наши View
    fun bind(film: Film) {
        //Устанавливаем заголовок
        title.text = film.title
        //Устанавливаем постер
//        poster.setImageResource(film.poster)
        //Вместо того, чтобы добавлять картинки в коде, добавим добавление через библиотеку Glide
        //Указываем контейнер, в котором будет "жить" наша картинка
        Glide.with(itemView)
            //Загружаем сам ресурс
            .load(film.poster)
            //Центрируем изображение
            .centerCrop()
            //Указываем ImageView, куда будем загружать изображение
            .into(poster)
        //Устанавливаем описание
        description.text = film.description
        //устанавливаем рейтинг
        ratingDonut.setProgress((film.rating * 10).toInt())
    }


}