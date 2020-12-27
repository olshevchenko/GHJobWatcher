# Change Log of the GHJobWatcher project
All notable changes to this project will be documented in this file.

Here are **types of changes** used in the file:  
- #### Milestone
- #### Processed
- #### Added
- #### Changed
- #### Fixed
- #### ToDo



##2020-12-27
#### Fixed
- GlideWrapper - исправлена поддержка светлой-тёмной темы Андроид в показе CircularProgressDrawable - успех  
- [list_view_item.xml] - скорректирована обрезка длинных полей текста - успех

##2020-12-25
#### Added
- Модель GitHubApiJob упростилась и теперь все поля (id!!) стали nullable- 
- Конверсия и р-та с default-значениями полей перенесены во ViewModel


##2020-12-22
#### Added
- Добавление ImageView с логотипом фирмы - как в список, так и в экран детализации работы
- Работа с Glide + caching - в отдельном классе-утилите GlideWrapper

#### Changed
- Перенос поля 'how_to_apply' вместе с парсингом (spanned) html-тегов в экран детализации


##2020-12-15
#### Added
- Добавление Toast при ошибках с сетью
- Развитие GitHubApiStatus (превращение в GitHubApiState)


##2020-12-14
#### Added
- Коммит в GitHub

#### Fixed
- Возня со стайлингом ActionBar (день-ночь) - успех


##2020-12-13
#### Added
- Добавление ActionBar в экран детализации работы

##2020-12-12
#### Fixed
- Решение НЕсрабатывания нажатия на элементе списка - успех
- Парсинг html-тегов в description - успех 

##2020-12-11
#### Processed
- Поиск решения НЕсрабатывания нажатия на элементе списка - продолжение

#### Fixed
- Удаление (парсинг!)  html-тегов в description - частично 

#### Added
- Добавление [CHANGELOG.md] в проект
- Выкладывание проекта на github: https://github.com/olshevchenko/GHJobWatcher

##2020-12-10
#### Processed
- Поиск решения НЕсрабатывания нажатия на элементе списка

#### Added
- Добавление меню приложения (действие “Refresh”)
- Добавление “SwipeToRefresh” функц-ти с адекватным отображением статусов обмена с сетью

#### Fixed
- Удаление (парсинг!)  html-тегов в how_to_apply - успех


##2020-12-09
#### Processed
- Поиск способа удаления html-тегов

#### Added
- Добавлены стандартные цвета для успешной работы в светлой / тёмной темах Android

#### Fixed
- Исправление парсинга серверных дат для работы с заголовками секций (“сегодня” / “вчера” / “31/12/2020”) - успех


##2020-12-08
#### Milestone
- Сборка, запуск приложения - успех


