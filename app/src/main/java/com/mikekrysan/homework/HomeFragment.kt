package com.mikekrysan.homework

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter  //24.8

    //24.2:
    val filmsDataBase = listOf(
        Film("Friends", R.drawable.friends_cd66, "Злоключения группы друзей, преодолевающих ловушки работы, жизни и любви на Манхэттене."),
        Film("Frozen", R.drawable.frozen, "Юная принцесса Анна из Эренделла мечтает встретить настоящую любовь на коронации своей сестры Эльзы. Судьба отправляет ее в опасное путешествие в попытке положить конец вечной зиме, охватившей королевство. Ее сопровождают доставщик льда Кристофф, его северный олень Свен и снеговик Олаф. В приключении, где она узнает, что на самом деле означают дружба, мужество, семья и настоящая любовь."),
        Film("Jungle-cruise", R.drawable.jungle_cruise, "Доктор Лили Хоутон обращается за помощью к остроумному шкиперу Фрэнку Вольфу, чтобы тот отвез ее вниз по Амазонке на своей ветхой лодке. Вместе они ищут древнее дерево, обладающее исцеляющей силой, — открытие, которое изменит будущее медицины."),
        Film("Jurassic world dominion", R.drawable.jurassic_world_dominion, "Сюжет неизвестен."),
        Film("Shindlers list", R.drawable.schindlers_list, "Реальная история о том, как бизнесмен Оскар Шиндлер спас от нацистов более тысячи еврейских жизней, пока они работали рабами на его фабрике во время Второй мировой войны."),
        Film("Stranger things", R.drawable.stranger_things, "Когда пропадает мальчик, в маленьком городке раскрывается тайна, связанная с секретными экспериментами, ужасающими сверхъестественными силами и одной странной маленькой девочкой."),
        Film("The batman", R.drawable.the_batman, "На втором году борьбы с преступностью миллиардер Брюс Уэйн, также известный как Бэтмен, берет на себя таинственного серийного убийцу, известного как Загадочник."),
        Film("The lord of the rings", R.drawable.the_lord_of_the_rings, "Молодой хоббит Фродо Бэггинс, унаследовавший от своего дяди Бильбо таинственное кольцо, должен покинуть свой дом, чтобы оно не попало в руки злого создателя. Попутно создается товарищество, чтобы защитить хранителя кольца и убедиться, что кольцо прибыло в конечный пункт назначения: Роковая гора, единственное место, где его можно уничтожить."),
        Film("Toy story 4", R.drawable.toy_story_4, "Вуди всегда был уверен в своем месте в мире и в том, что его приоритетом является забота о своем ребенке, будь то Энди или Бонни. Но когда Бонни с неохотой добавляет в свою комнату новую игрушку по имени Форки, путешествие вместе со старыми и новыми друзьями покажет Вуди, насколько большим может быть мир для игрушки."),
        Film("The lion king", R.drawable.the_lion_king, "Молодой принц-лев изгнан из своей гордыни своим жестоким дядей, который утверждает, что убил своего отца. Пока дядя правит железной лапой, принц растет за пределами Саванны, живя по философии: никаких забот до конца своих дней. Но когда прошлое начинает преследовать его, молодой принц должен решить свою судьбу: останется ли он изгоем или столкнется со своими демонами и станет тем, кем он должен быть?"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Находим наш RV //24.10Start
        main_recycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвоим layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        //Кладем нашу БД в RV //24.10 End
        filmsAdapter.addItems(filmsDataBase)

        //Для того, чтобы выбиралось все поле поиска при нажатии на нем, а не только по иконке:
        search_view.setOnClickListener {
            search_view.isIconified = false
        }

        //Подключаем слушателя изменений введенного текста в поиска
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String?): Boolean {
                //Если ввод пуст то вставляем в адаптер всю БД
                    if (newText!!.isEmpty()) {
                        filmsAdapter.addItems(filmsDataBase)
                        return true
                    }
                //Фильтруем список на поискк подходящих сочетаний
                val result = filmsDataBase.filter {
                    //Чтобы все работало правильно, нужно и запрос, и имя фильма приводить к нижнему регистру
                    newText?.let { it1 -> it.title.toLowerCase(Locale.getDefault()).contains(it1.toLowerCase(Locale.getDefault())) }!!
                }
                //Добавляем в адаптер
                filmsAdapter.addItems(result)
                return true
            }
        })
    }


}