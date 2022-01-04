echo off
set var=traffic
copy .\build\WEB-INF\classes\%var%\*.class .\WEB-INf\classes\%var%\.
:done
