# Instructions for running the program

Because not everyone has Apache Netbeans IDE installed on their computer, and the instructions for running a Java program vary depending on the operating system and 
installation folders, the easiest way to run this program is by using the TUNI remote environment through an application or the browser:
https://www.tuni.fi/fi/it-palvelut/kasikirja/ohjelmistot/ohjelmiston-etakaytto-0/tuni-virtual-desktop-azure-virtual-desktop

Once you have logged into the environment...
1. Open Windows PowerShell,
1. Navigate to a preferred folder,
2. Clone the repository (this might take a while...),
3. Open Apache NetBeans IDE.
4. Within the IDE, navigate "File" > "Open Project" > Choose the "java-group" folder and within the "weatherApp" project, and click "Open Project".
If the IDE asks about plugin installation, install the plugins.
5. Once the IDE has done loading the project folder, hit the green arrow at the top or navigate "Run" > "Run Project". 
The first time running the IDE builds the project, so this can take a while. If the program does not start after the build is complete, hit the green
arrow again.
6. Voilà, the program should be running now!

**I do not want to use the remote environment**

If you know what you're doing, you can clone the repository to your computer and try running the program with the command lines below:
```
javac --module-path "Path to your javafx lib folder" --add-modules javafx.controls,javafx.fxml App.java
java --module-path "Path to your javafx lib folder" --add-modules javafx.controls,javafx.fxml App
```
If nothing else works, the final option is to install Apache NetBeans IDE on your computer with these instructions: https://plus.tuni.fi/comp.cs.140/spring-2022/modules_01/01_ohjelmointiymparisto/ 
(sections "2.3 Java: ohjelmointiympäristö" and "2.3.2 Java IDE ja ensimmäinen ohjelma")
