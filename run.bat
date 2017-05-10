call  build.bat
java -cp "lib/*;build/*" ^
com.shpota.chat.model.net.Server
java -cp "lib/*;build/*" ^
com.shpota.chat.controller.ChatController
pause