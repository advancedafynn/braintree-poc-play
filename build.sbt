import com.typesafe.sbt.less.Import.LessKeys
import com.typesafe.sbt.web.Import.Assets

name := "playbraintree"

version := "1.0"

lazy val `playbraintree` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

includeFilter in (Assets, LessKeys.less) := "*.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

LessKeys.compress := true



includeFilter in (Assets, CoffeeScriptKeys.coffeescript) := "*.coffee"

//fork in run := true
