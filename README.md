
# MDVK-Arch-Starter

> AUTHOR			:		 **`ADITYA PRATAMA`**

> DATE : **`April 2019`**


#### FLOW DIAGRAM

![MD|VK](https://raw.githubusercontent.com/abehbatre/MDVK-Arch-Starter/master/architecture.png)

  

#### PROFLING RESULT

![MD|VK](https://raw.githubusercontent.com/abehbatre/MDVK-Arch-Starter/master/profiling_test.png)

  

#### CLEAN PROJECT STRUCTURE & EASY TO UNDERSTAND

![MD|VK](https://raw.githubusercontent.com/abehbatre/MDVK-Arch-Starter/master/profiling_test.png)

  
  

#### Setup included :

---

-  **UI**

    - Adaptation from `MaterialX`

-  **Data Binding**

    - Using Android Data Binding Library

-  **Splash Screen**

    - Right way to implement splash screen [[see detail](https://www.bignerdranch.com/blog/splash-screens-the-right-way/)]

-  **SharedPreference**

    -  `Singleton` SharedPreference with `synchronized` easy to use.

    - get value example :

    `object = SharedPref.getInstance().getString(KEY_USERNAME, "");`

    - set value example :

    `SharedPref.getInstance().saveString(object);`

-  **Authentication Flow**

    - Using Retrofit & ResponseInterceptor for request & response handling

    - Dummy API using `https://api.github.com/`

-  **Room**

    - Using room for data persistance

-  **RecyclerView Sample**

    - Sample recyclerview implementation using viewholder & databinding

    - Using `User` as dummy object representation

    - See `MainAdapter` for details

-  **Android M permission handler using `Dexter`**

    - Handle permission for Android M and above

-  **Reusable Style**

    - All colors are available inside `colors.xml`

    - Styles are available inside `styles.xml`

-  **Utilities and Helper classes**

    -  `MDVKHelper` this class have 6 inner class :
      ```DIALOG_TOOLS, NETWORK_TOOLS, STRING_TOOLS, WINDOW_TOOLS, DATE_PICKER```

      LocalNotification

    *Tools means `helpers`* , just grouping by type helper ~

    -  `PermissionHelper` handler request permission with dexter

    -  `PicassoHelper` Picasso Loader for load large images in Android and avoiding the out of memory error`

    - Item Adapter Animation / Effect : `ItemAdapterAnimation` , `SpacesItemDecoration` , `SpacingItemDecoration` , `ViewAnimation`
