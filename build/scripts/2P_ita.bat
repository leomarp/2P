@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  2P_ita startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and 2_P_ITA_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\2P_ita-1.0-SNAPSHOT.jar;%APP_HOME%\lib\slf4j-simple-1.8.0-beta4.jar;%APP_HOME%\lib\javalin-3.9.0.jar;%APP_HOME%\lib\thymeleaf-3.0.5.RELEASE.jar;%APP_HOME%\lib\jjwt-jackson-0.10.5.jar;%APP_HOME%\lib\jackson-databind-2.10.1.jar;%APP_HOME%\lib\hibernate-entitymanager-5.4.17.Final.jar;%APP_HOME%\lib\hibernate-core-5.4.17.Final.jar;%APP_HOME%\lib\h2-1.4.197.jar;%APP_HOME%\lib\jetty-http-spi-9.4.30.v20200611.jar;%APP_HOME%\lib\jaxws-rt-2.3.1.jar;%APP_HOME%\lib\rt-2.3.1.jar;%APP_HOME%\lib\jaxb-runtime-2.4.0-b180830.0438.jar;%APP_HOME%\lib\unirest-java-3.8.06-standalone.jar;%APP_HOME%\lib\jjwt-impl-0.10.5.jar;%APP_HOME%\lib\jjwt-api-0.10.5.jar;%APP_HOME%\lib\gson-2.8.6.jar;%APP_HOME%\lib\slf4j-api-1.8.0-beta4.jar;%APP_HOME%\lib\jetty-webapp-9.4.29.v20200521.jar;%APP_HOME%\lib\websocket-server-9.4.29.v20200521.jar;%APP_HOME%\lib\jetty-servlet-9.4.29.v20200521.jar;%APP_HOME%\lib\jetty-security-9.4.29.v20200521.jar;%APP_HOME%\lib\jetty-server-9.4.29.v20200521.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.3.71.jar;%APP_HOME%\lib\ognl-3.1.12.jar;%APP_HOME%\lib\attoparser-2.0.3.RELEASE.jar;%APP_HOME%\lib\unbescape-1.1.4.RELEASE.jar;%APP_HOME%\lib\jackson-annotations-2.10.1.jar;%APP_HOME%\lib\jackson-core-2.10.1.jar;%APP_HOME%\lib\hibernate-commons-annotations-5.1.0.Final.jar;%APP_HOME%\lib\jboss-logging-3.3.2.Final.jar;%APP_HOME%\lib\javax.persistence-api-2.2.jar;%APP_HOME%\lib\javassist-3.24.0-GA.jar;%APP_HOME%\lib\byte-buddy-1.10.10.jar;%APP_HOME%\lib\antlr-2.7.7.jar;%APP_HOME%\lib\jboss-transaction-api_1.2_spec-1.1.1.Final.jar;%APP_HOME%\lib\jandex-2.1.3.Final.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\jaxws-api-2.3.1.jar;%APP_HOME%\lib\jaxb-api-2.4.0-b180830.0359.jar;%APP_HOME%\lib\javax.activation-api-1.2.0.jar;%APP_HOME%\lib\dom4j-2.1.3.jar;%APP_HOME%\lib\policy-2.7.5.jar;%APP_HOME%\lib\txw2-2.4.0-b180830.0438.jar;%APP_HOME%\lib\istack-commons-runtime-3.0.7.jar;%APP_HOME%\lib\streambuffer-1.5.6.jar;%APP_HOME%\lib\saaj-impl-1.5.0.jar;%APP_HOME%\lib\stax-ex-1.8.jar;%APP_HOME%\lib\FastInfoset-1.2.15.jar;%APP_HOME%\lib\javax.xml.soap-api-1.4.0.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\javax.jws-api-1.1.jar;%APP_HOME%\lib\gmbal-api-only-3.1.0-b001.jar;%APP_HOME%\lib\mimepull-1.9.10.jar;%APP_HOME%\lib\ha-api-3.1.9.jar;%APP_HOME%\lib\woodstox-core-5.1.0.jar;%APP_HOME%\lib\stax2-api-4.1.jar;%APP_HOME%\lib\httpmime-4.5.12.jar;%APP_HOME%\lib\httpclient-4.5.12.jar;%APP_HOME%\lib\httpcore-nio-4.4.13.jar;%APP_HOME%\lib\httpasyncclient-4.1.4.jar;%APP_HOME%\lib\websocket-servlet-9.4.29.v20200521.jar;%APP_HOME%\lib\javax.servlet-api-3.1.0.jar;%APP_HOME%\lib\websocket-client-9.4.29.v20200521.jar;%APP_HOME%\lib\jetty-client-9.4.29.v20200521.jar;%APP_HOME%\lib\jetty-http-9.4.29.v20200521.jar;%APP_HOME%\lib\websocket-common-9.4.29.v20200521.jar;%APP_HOME%\lib\jetty-io-9.4.29.v20200521.jar;%APP_HOME%\lib\jetty-xml-9.4.29.v20200521.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.3.71.jar;%APP_HOME%\lib\kotlin-stdlib-1.3.71.jar;%APP_HOME%\lib\javax.activation-1.2.0.jar;%APP_HOME%\lib\management-api-3.0.0-b012.jar;%APP_HOME%\lib\httpcore-4.4.13.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\commons-codec-1.11.jar;%APP_HOME%\lib\jetty-util-9.4.29.v20200521.jar;%APP_HOME%\lib\websocket-api-9.4.29.v20200521.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.3.71.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\activation-1.1.jar

@rem Execute 2P_ita
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %2_P_ITA_OPTS%  -classpath "%CLASSPATH%" Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable 2_P_ITA_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%2_P_ITA_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
