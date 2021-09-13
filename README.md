# LawRights

Hello :hand: and welcome to **LawRights** android project

This project was developed as a coding exercise for the android developer role at [Law Pavillion](http://lawpavilion.com/)

### Libraries/APIs
- Kotlin
- Material design
- Android navigation components
- Hilt
- Coroutine and Flow
- Paging 3
- Room
- Retrofit
- Timber
- MockWebServer
- MockK

### Installation
- Clone the project from https://github.com/segunfrancis/LawRights.git
- Build and run the project on android studio

### Requirements
- Minimum SDK: Android Lollipop (SDK level 21)

### Architecture
This project uses MVVM (Model-View-ViewModel) architecture. The project is structured into different modules for separation of concern.

 - Modules: 
    - app
    - common
    - data
    - feature
 
 
 - app: The app module is the main entry point of the project. It contains the main navigation graph of the project
 - common: This module contains classes and resources that are used by other feature modules. It contains drawables, strings and styles resources.
 - data: This module contains 2 sub-modules - remote and local layer. The remote layer contains network calls created with Retrofit. The local layer contains Room database implementations.
 - feature: This module contains sub-modules of different features of the app. There are currently 2 features - home and my_rights. When more features are added to this project, they will be added to the feature module as sub-modules.
 
 
### Dependency graph
<img src="https://github.com/segunfrancis/LawRights/blob/master/Group%2042.png" alt="image describing the dependency of each module">


### Screenshots
<p align="middle">
 <img src="https://github.com/segunfrancis/LawRights/blob/master/screenshots/device-2021-09-13-155653.png" alt="dashboard screenshot" width="33%">
 <img src="https://github.com/segunfrancis/LawRights/blob/master/screenshots/device-2021-09-13-155800.png" alt="my rights screenshot collapsed" width="33%">
 <img src="https://github.com/segunfrancis/LawRights/blob/master/screenshots/device-2021-09-13-155837.png" alt="my rights screenshot expanded" width="33%">
</p>


### Challenges
The most significant challenge I faced was using the Paging 3 android library to display the list of rights. The library returns a `PagingData` which is a pure android API. This made it impossible for me to use clean architecture in this project. This is due to the fact that the **domain** layer of the android clean architecture is supposed to be a pure Kotlin module. I overcame this by using the MVVM architecture alone. 
Another issue I faced was as a result of difficulty in caching the paged data. Managing the states was also difficult because the method that submits the paged list to the Paging adapter was a suspend function that had to be called from a Coroutine, this made updating the views difficult becuase view updates have to be carried out on the main (UI) thread.
