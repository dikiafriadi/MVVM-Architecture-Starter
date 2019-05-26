# Getting Started

#### Before started, I recommend you to Install Plugin

- RoboPOJOGenerator
    
    see on `data/remote/api_response` this packages auto generate by Robo POJO Generator .

- DataBinding Support

    to convert all layout `(XML)` to databinding

---

## Rename Application Package Name

- First click one on your package and then click setting icon on Android Studio
- Close / Unselect Compact Empty Middle Packages
- Then, right click your package and rename it
- Right click on the root folder of your project
- Click "Open Module Setting"
- Go to the Flavours tab
- Change the application ID with same name of your package name before > Pres OK
- Open Android studio > app > java > com.your.project > errorhandler > CustomActivityOnCrash.java
- Change "PACKAGE_NAME" with your package name

> reference : [ [click me] (http://stackoverflow.com/questions/16804093/android-studio-rename-package/29092698#29092698)]

## Change Application Name
- Open Android studio > app > res > values > string.xml
- Select 'strings.xml'
- Enter your app name inside "app_name" string tag :
- `<string name="app_name">YOUR APP NAME</string>`

## Reflector **MDVK** to your appname

`MDVK` class -> app starter, reflector, and re-configure this class .
     
## Update CONSTANT
`core/CONSTANT` -> app constant, reconfigure this class. 

---