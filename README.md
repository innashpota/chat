# CHAT #
***

##Додаток для обміну текстовими повідомленнями.

Додаток базується на java sockets, JDBC, Postgres SQL,
Swing для графічного інтерфейсу та LOG4J як 
система логування.

### Збірка і запуск проекту

1. Зібрати docker image для бази даних 
[build-db-image.bat](./build-db-image.bat).

2. Запустити базу даних [start-db.bat](./start-db.bat).

3. Запустити сервер [start-server.bat](./start-server.bat) 
(перед запуском проект буде автоматично буде 
зкомлільований та запакований в jar архів).

4. Запустити клієнт [start-client.bat](./start-client.bat).

Головне вікно додатку виглядає наступним чином

![main-window](./samples/main-window.PNG)
