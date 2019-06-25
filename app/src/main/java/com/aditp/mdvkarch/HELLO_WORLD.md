# Getting Started

~ Hello World ~ 

> Learn Reference :
- https://developer.android.com/topic/libraries/architecture/lifecycle
- https://developer.android.com/topic/libraries/architecture/viewmodel
- https://developer.android.com/topic/libraries/data-binding/
- https://developer.android.com/topic/libraries/architecture/livedata
- https://developer.android.com/topic/libraries/architecture/room
- https://developer.android.com/jetpack/#architecture-components
- https://amitshekhar.me/Fast-Android-Networking/


##### Basic Explained

| architecture      | description                   |
|---                | ---                           |
| Model             | Response / Entity             |
| View              | Activity / Fragment (UI)      |
| ViewModel         | Business Logic                |


##### Basic Rules
- UI can't access Repository directly, let alone a model, **access via `viewModel`** ~
- every UI should have `ViewModel`
- do not edit  **abstract class** | example: `BaseActivity` .


### explained structure package

| package           | description                                               |
|---                | ---                                                       |
| core              |  inti dari struktur codingan                              |
| data              |  pusat management data                                    |
| data/local        |  data local (database)                                    |
| data/remote       |  data remote (internet / server)                          |
| data/model        |  entity                                                   |
| helper            |  class helper                                             |
| helper/utils      |  tool utility yang harusnya tidak perlu di modifikasi lagi|
| ui                |  activity, fragment, viewModel                            |



### plugin support (recommend to install)
> robopojo generator
> databinding converter
> Exynap (optional)
---

# TODO

#### 1.) Rename Application Package Name

- First click one on your package and then click setting icon on Android Studio
- Close / Unselect Compact Empty Middle Packages
- Then, right click your package and rename it
- Right click on the root folder of your project
- Click "Open Module Setting"
- Go to the Flavours tab
- Change the application ID with same name of your package name before > Press OK
- Change `"PACKAGE_NAME"` with your package name

> reference : [ [click me ...](https://stackoverflow.com/questions/16804093/android-studio-rename-package/29092698#29092698)]

#### 2.) Change Application Name
- Open Android studio > app > res > values > string.xml
- Select 'strings.xml'
- Enter your app name inside "app_name" string tag :
- `<string name="app_name">YOUR APP NAME</string>`

#### 3.) Reflector **MDVK** to your appname

- `MDVK` class -> app starter, reflector, and re-configure this class .
     
#### 4.) Remove Unused Class 

---

Ask something? [write here ...](https://github.com/abehbatre/MVVM-Architecture-Starter/issues)