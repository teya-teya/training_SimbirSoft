@echo off
chcp 65001 > nul
echo ========================================
echo ЗАПУСК УПАВШИХ ТЕСТОВ
echo ========================================

if not exist target\failed-tests.txt (
    echo Файл с упавшими тестами не найден!
    pause
    exit /b
)

echo Очистка старых результатов...
if exist target\allure-results-failed rmdir /s /q target\allure-results-failed
if exist target\allure-report-failed rmdir /s /q target\allure-report-failed

echo Сборка проекта...
call mvn clean compile

echo Запуск упавших тестов...
java -cp "target\test-classes;target\classes;%USERPROFILE%\.m2\repository\*\*\*.jar" ^
  -Dallure.results.directory=target/allure-results-failed ^
  utils.FailedTestRunner

echo Генерация Allure отчета...
call allure generate target/allure-results-failed -o target/allure-report-failed --clean

echo Открытие отчета...
start chrome target/allure-report-failed/index.html

echo ========================================
echo ГОТОВО!
echo ========================================
pause