# UlearnJavaProject

1. Для хранения данных из csv создадим 2 файла - PlayerIndicators и Team
Team - описывает команду и хранит ее название и список игроков.
Player - описывает игрока, хранит в себе имя, позиция, вес, рост, возраст
![image](https://user-images.githubusercontent.com/104633083/211038097-f4638838-5ca0-495f-a34d-c640afc0a0de.png)
![image](https://user-images.githubusercontent.com/104633083/211038228-79db6075-eab5-4120-9af2-486009b16422.png)
<br /><br /><br />

2. Создаём класс CSVHandler, который будет обрабатывать csv файл
![image](https://user-images.githubusercontent.com/104633083/211038442-8c92a8cc-6e72-4ae8-9dc7-783571e4f543.png)
<br /><br /><br />

3. Создаеём класс SQLHandler, который будет отвечать за работу с базой данных. Так же создаем две таблицы в базе данных - для команд и игроков.
![image](https://user-images.githubusercontent.com/104633083/211038917-013cb6da-60ef-4a6f-8def-2e23ba2c55de.png)

4. Читаем данные из csv файла и заполняем ими таблицы в базе данных с помощью метода insertTeam.
![image](https://user-images.githubusercontent.com/104633083/211039711-c62ef7fa-ae58-49be-affc-a63735d5d378.png)
![image](https://user-images.githubusercontent.com/104633083/211040305-618ed5ed-3423-45ea-a994-fc166c8722d4.png)

5. Создадим в классе SQLHandler метод getAllData, который будет возвращать все аднные из базы данных и с его помощью выведем эти данные в консоль.
![image](https://user-images.githubusercontent.com/104633083/211040804-77526564-571d-40cd-a107-0f57f2f64364.png)
![image](https://user-images.githubusercontent.com/104633083/211040861-3ce2dfe5-b8a5-4aa5-b503-3d48c6861798.png)
![image](https://user-images.githubusercontent.com/104633083/211041024-ca6f4289-d768-46da-8c71-0da06ee53ec0.png)

8. Реализуем логику построения графика среднего возраста команд для первого задания.
![image](https://user-images.githubusercontent.com/104633083/211041595-b9a2ddcb-d0ca-4e74-a5df-e34188bf4362.png)

7. Итоговый график.
![image](https://user-images.githubusercontent.com/104633083/211041286-d8863b71-fe4e-43d8-a0f6-c3ec3cc51cc2.png)

8. Реализуем логику для второго задания.
![image](https://user-images.githubusercontent.com/104633083/211041933-ddfda7df-f648-4af6-8965-e66636bc3b12.png)

9. Реализуем логику для третьего задания
![image](https://user-images.githubusercontent.com/104633083/211042063-e717e776-dc46-4537-a5cf-908cddae7096.png)

10. Результат выполнения заданий:
![image](https://user-images.githubusercontent.com/104633083/211042205-cfc937ee-54d2-4b15-a15c-486064ce4af7.png)







