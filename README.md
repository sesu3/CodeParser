# TMParser-J
##概説
Java言語のソースコードからクラス及びメソッドの情報を取得するツールです．  
解析の対象としては単一のソースファイルだけではなく，それらをまとめたディレクトリ，あるいはバージョン管理リポジトリに対して行うことができます．  
解析した結果は標準出力の他，ファイル出力，データベースへの登録に対応しています．

##想定する環境
+ Linux
+ mySQLが使用可能（-db*オプションを使用しなければ不要）

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
  <thead>
    <tr>
      <th>オプション</th>
      <th>引数</th>
      <th>意味</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>-outfile</td>
      <td>結果出力先のパス</td>
      <td>解析結果を指定したファイルに出力する</td>
    </tr>
    <tr>
      <td>-invisible</td>
      <td>無し</td>
      <td>解析結果を標準出力しない</td>
    </tr>
    <tr>
      <td>-ignore-err</td>
      <td>無し</td>
      <td>コンパイル時にエラーが発生したファイルを無視．これを指定すると，データベース使用時に解析した対象として記録されなくなる．</td>
    </tr>
    <tr>
      <td>-git</td>
      <td>.gitディレクトリのあるディレクトリのパス</td>
      <td>指定したディレクトリをGitリポジトリと見なして解析する．開発履歴上で追加・修正されたjavaファイルを全て解析対象とする．</td>
    </tr>
    <tr>
      <td>-dbuser</td>
      <td>データベースのユーザ名</td>
      <td>使用するデータベースのユーザ名を指定．データベース使用時に指定する．</td>
    </tr>
    <tr>
      <td>-dbpw</td>
      <td>データベースのパスワード</td>
      <td>使用するデータベースのパスワードを指定．データベース使用時に指定する．</td>
    </tr>
    <tr>
      <td>-dbname</td>
      <td>データベースの名前</td>
      <td>使用するデータベースの名前を指定．データベース使用時に指定．解析時に新規作成するためまだ使用されていない名前を指定してください．</td>
    </tr>
  </tbody>
</table>

###出力
一行目は解析対象の種類を表しています．
<table>
  <tr><td>class</td><td>クラス</td></tr>
  <tr><td>interface</td><td>インターフェース</td></tr>
  <tr><td>method</td><td>メソッド</td></tr>
  <tr><td>constructor</td><td>コンストラクタ</td></tr>
</table>
各項目の二行目以降の意味は以下の通りです．
<table>
  <thead>
    <tr>
      <th>項目</th>
      <th>表示対象</th>
      <th>意味</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>name:</td>
      <td>class,interface,method,constructor</td>
      <td>識別子．classとinterfaceはFQCN</td>
    </tr>
    <tr>
      <td>modifier:</td>
      <td>class,interface,method,constructor</td>
      <td>修飾子．カンマ区切りで出力</td>
    </tr>
    <tr>
      <td>range:</td>
      <td>class,interface,method,constructor</td>
      <td>定義範囲．開始行と終了行をカンマ区切りで表示</td>
    </tr>
    <tr>
      <td>super:</td>
      <td>class,interface</td>
      <td>スーパークラス名</td>
    </tr>
    <tr>
      <td>field:</td>
      <td>class,interface</td>
      <td>フィールド．カンマ区切りで出力．修飾子と型，名前をまとめて表示する．</td>
    </tr>
    <tr>
      <td>returnType:</td>
      <td>method,constructor</td>
      <td>メソッドの戻り値の型．コンストラクタはnullと表示．</td>
    </tr>
    <tr>
      <td>arguments:</td>
      <td>method,constructor</td>
      <td>引数．カンマ区切りで出力</td>
    </tr>
  </tbody>
</table>

##Abstract
This is the tool that collect information of CLASS and METHOD in Java source file.  
By this tool, you can parse about not only a source file but also the directory that contain them or version controle system repository.  
It supports standard, file, database output.

##Assumed environment
+ Linux
+ mySQL is available（unnecessary if the option -db* is not used.）

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


##Usage
`java -jar TMParser-J.jar [オプション] (ファイルパス|ディレクトリパス|リポジトリパス)`  
I recommend that you indicate the path with full path.

###Options
<table>
  <thead>
    <tr>
      <th>option</th>
      <th>argument</th>
      <th>meaning</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>-outfile</td>
      <td>a destination file path of persing result printed out</td>
      <td>print out persing result to an indicated file.</td>
    </tr>
    <tr>
      <td>-invisible</td>
      <td>none</td>
      <td>unuse standard output for result.</td>
    </tr>
    <tr>
      <td>-ignore-err</td>
      <td>none</td>
      <td>ignore the file that contains one or more  error. you do not register the file information as persing target to database.</td>
    </tr>
    <tr>
      <td>-git</td>
      <td>the directory path that contain .git directory.</td>
      <td>parse indicated directory as a git repository. parse added or modified java files on the development history.</td>
    </tr>
    <tr>
      <td>-dbuser</td>
      <td>the user name of database</td>
      <td>the user name of database you use. this option is indicated when you use database.</td>
    </tr>
    <tr>
      <td>-dbpw</td>
      <td>the password of database</td>
      <td>give password of the database you use. this option is indicated when you use database.</td>
    </tr>
    <tr>
      <td>-dbname</td>
      <td>the name of database</td>
      <td>give name of the database you use. this option is indicated when you use database. please give not exist name because database will be created when you start parsing.</td>
    </tr>
  </tbody>
</table>

###Output
the first line of each item means kind of target.
and then, the second line or later meanings are below.
<table>
  <thead>
    <tr>
      <th>category</th>
      <th>what type does it have</th>
      <th>mean</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>name:</td>
      <td>class,interface,method,constructor</td>
      <td>identifiers．class and interface are printed out with FQCN</td>
    </tr>
    <tr>
      <td>modifier:</td>
      <td>class,interface,method,constructor</td>
      <td>modifiers．a comma-delimited.</td>
    </tr>
    <tr>
      <td>range:</td>
      <td>class,interface,method,constructor</td>
      <td>its declaration range．a comma-delimited.</td>
    </tr>
    <tr>
      <td>super:</td>
      <td>class,interface</td>
      <td>super class name.</td>
    </tr>
    <tr>
      <td>field:</td>
      <td>class,interface</td>
      <td>fields. a comma-delimited. printed with modifiers, type and name.</td>
    </tr>
    <tr>
      <td>returnType:</td>
      <td>method,constructor</td>
      <td>type of method's return value. if it is constructor, print null.</td>
    </tr>
  </tbody>
</table>
