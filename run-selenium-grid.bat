@echo off
REM -------------------------------
REM Запуск Selenium Grid Hub и Node
REM -------------------------------

REM Путь к selenium-server jar в корне проекта
SET SELENIUM_SERVER=selenium-server-4.41.0.jar

REM Запуск Hub
start cmd /k "java -jar %SELENIUM_SERVER% hub"

REM Пауза, чтобы hub успел стартовать
timeout /t 5 /nobreak >nul

REM Запуск Node с 2 браузерами
start cmd /k "java -jar %SELENIUM_SERVER% node --detect-drivers true --max-sessions 2 --hub http://localhost:4444"

echo Selenium Grid запущен!
pause