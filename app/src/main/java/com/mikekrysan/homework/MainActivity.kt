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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()

        //Находим наш RV //24.10Start
        main_recycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    //24.14
                    val bundle = Bundle()
                    //Первым параметром указывается ключ, по которому потом будем искать, вторым сам
                    //передаваемый объект
                    bundle.putParcelable("film", film)
                    //24.12 Запускаем наше активити
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    //24.14 Прикрепляем бандл к интенту
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            })
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвоим layoutmanager
            layoutManager = LinearLayoutManager(this@MainActivity)
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        //Кладем нашу БД в RV //24.10 End
        filmsAdapter.addItems(filmsDataBase)
    }


    private fun initNavigation() {
        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

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