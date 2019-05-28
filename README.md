
# MDVK-Android-Architecture-Starter 
> PROJECT DATE			:		 **`APRIL 2019`**



#### 💼 FLOW DIAGRAM (MVVM)

![MD|VK](https://raw.githubusercontent.com/abehbatre/MDVK-Arch-Starter/master/architecture.png)

  

#### 📷 PROFLING RESULT

![MD|VK](https://raw.githubusercontent.com/abehbatre/MDVK-Arch-Starter/master/profiling_test.png)

  

#### 💎 CLEAN PROJECT STRUCTURE & EASY TO UNDERSTAND

![MD|VK](https://raw.githubusercontent.com/abehbatre/MDVK-Arch-Starter/master/project_architecture.png)


#### Setup included : 📦
---

-  **UI**

    - Adaptation from <u>**`MaterialX`**</u> 🍺

-  **Data Binding**

    - Using Android Data Binding Library

-  **Splash Screen**

    - Right way to implement splash screen [[see detail](https://www.bignerdranch.com/blog/splash-screens-the-right-way/)]

-  **SharedPreference**

    -  *Singleton* SharedPreference with *synchronized* easy to use.
    
    get value example :
    ```javascript
    object = SharedPref.getInstance().getString(KEY_USERNAME, "");
    ```

    set value example :
    ```javascript
    SharedPref.getInstance().saveString(object);
    ```

-  **Authentication Flow**

    - Using Retrofit & ResponseInterceptor for request & response handling
    - Dummy API using `https://api.github.com/`

-  **Room**
    - Using room for data persistance

-  **RecyclerView Sample**
      - Sample recyclerview implementation using viewholder & databinding
    - See `MainAdapter` for details

-  **Android M permission handler using `Dexter`**

    - Handle permission for Android M and above

-  **Reusable Style**

    - All colors are available inside `colors.xml`

    - Styles are available inside `styles.xml`

-  **Utilities and Helper classes**

    -  `MDVKHelper` this class have 6 inner class :
        -  DIALOG_TOOLS
        -  NETWORK_TOOLS
        -  STRING_TOOLS
        -  WINDOW_TOOLS
        -  DATE_PICKER
        -  LOCAL_NOTIFICATION
        
 **Tools** means **helpers** , just *grouping* by type helper

  -  **PermissionHelper**
  handler request permission with dexter
  
  -  **PicassoHelper**
  Picasso Loader for load large images in Android and avoiding the out of memory error
  
  -  **Item Adapter Utils :**
      - ItemAdapterAnimation
      - SpacesItemDecoration
      - SpacingItemDecoration
      - ViewAnimation

---

## ✅ TODO
- [x] Create example Retrofit get JSON Object
- [x] Create example Retrofit get JSON Array
- [x] Retrofit Error Handler & sample
- [x] Room with MVVM
- [x] Simple Custom toolbar & menu
- [x] Right way SplashScreen
- [ ] add library Data Binding Validator
- [ ] Rx Java 2 + Rx Android
- [ ] Documentation & Wiki
