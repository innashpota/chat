if not exist  .\build\classes md .\build\classes
javac -d ./build/classes ^
-classpath lib/log4j-1.2.17.jar ^
-sourcepath sources sources/com/shpota/chat/model/net/Server.java ^
sources/com/shpota/chat/controller/ChatController.java ^
sources/com/shpota/chat/model/*.java ^
sources/com/shpota/chat/view/*.java