# TMParser-J
##概説
Java言語のソースコードからクラス及びメソッドの情報を取得するツールです．  
解析の対象としては単一のソースファイルだけではなく，それらをまとめたディレクトリ，あるいはバージョン管理リポジトリに対して行うことができます．  
解析した結果は標準出力の他，ファイル出力，データベースへの登録に対応しています．

##想定する環境
+ Linux
+ mySQLが使用可能（-dbオプションを使用しなければ不要）

##必要なライブラリ  
このプロジェクトにはビルドパスに以下を含める必要があります．  
mysql-connector-java-*.jarはmysqlのサイトで入手します．その他はEclipseに元から含まれています．  
+ org.eclipse.core.contenttype_*.jar
+ org.eclipse.core.jobs_*.jar
+ org.eclipse.core.resources_*.jar
+ org.eclipse.core.runtime_*.jar
+ org.eclipse.equinox.common_*.jar
+ org.eclipse.equinox_preferences_*.jar
+ org.eclipse.jdt.core_*.jar
+ org.eclipse.osgi_*.jar
+ mysql-connector-java-*.jar

##使い方
`java -jar TMParser-J.jar [オプション] (ファイルパス|ディレクトリパス|リポジトリパス)`  
パスはフルパスで表記指定ください．

###オプション
<table>
  <tr><th>オプション</th><th>引数</th><th>意味</th></tr>
  <tr><td></td><td></td><td></td></tr>
  <tr><td></td><td></td><td></td></tr>
  <tr><td></td><td></td><td></td></tr>
  <tr><td></td><td></td><td></td></tr>
  <tr><td></td><td></td><td></td></tr>
  <tr><td></td><td></td><td></td></tr>
  <tr><td></td><td></td><td></td></tr>
  <tr><td></td><td></td><td></td></tr>
</table>

##Abstract
This is the tool that collect information of CLASS and METHOD in Java source file.  
By this tool, you can parse about not only a source file but also the directory that contain them or version controle system repository.  
It supports standard, file, database output.

##Assumed environment
+ Linux
+ mySQL is available（unnecessary if the option -db is not used.）

##Required libraries
You have to add build paths listed below．  
You can get mysql-connector-java-*.jar from mysql cite．Other jar files are contained in eclipse.  
+ org.eclipse.core.contenttype_*.jar
+ org.eclipse.core.jobs_*.jar
+ org.eclipse.core.resources_*.jar
+ org.eclipse.core.runtime_*.jar
+ org.eclipse.equinox.common_*.jar
+ org.eclipse.equinox_preferences_*.jar
+ org.eclipse.jdt.core_*.jar
+ org.eclipse.osgi_*.jar
+ mysql-connector-java-*.jar
