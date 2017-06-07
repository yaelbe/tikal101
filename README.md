# TMDb
A simple Android client for The Movie DB in Material Design

[![Get it on Google Play](http://www.android.com/images/brand/get_it_on_play_logo_small.png)](https://play.google.com/store/apps/details?id=com.tikal.tmdb)

## TL;DR
This project is an Android app which displays data from [The Movie Database](https://www.themoviedb.org) API.

## Screenshots
![Movie Search](https://raw.githubusercontent.com/thiagokimo/TMDb/master/screenshots/movie-search.png)
![Movie Detail](https://raw.githubusercontent.com/thiagokimo/TMDb/master/screenshots/movie-detail.png)
![Movie Images](https://raw.githubusercontent.com/thiagokimo/TMDb/master/screenshots/movie-images.png)
![Fullscreen Image](https://raw.githubusercontent.com/thiagokimo/TMDb/master/screenshots/fullscreen-image.png)

## The Mission
In this assignment I had to provide 3 main user features:

- Search for movies
- See details of a movie
- Open images of a movie

The following section explains how did I organized the architecture of my code.

## Architecture
The application is organized using a clean architecture approach consisting of 2 main layers:

- Presentation (app)
- Domain

### Presentation Layer
The view logic resides here. The **Model-View-Presenter** pattern was used to keep all the presentation logic into  *presenters* and away from the fragments and activities (which were considered only views). The presenters are composed of the use-case that perform their tasks on background, outside the UI Thread, and returning the results through a callback into the views.

Third-party dependencies:
```
compile 'com.android.support:appcompat-v7:22.1.1'
compile 'com.android.support:recyclerview-v7:21.0.0'
compile 'com.android.support:cardview-v7:21.0.0'
compile 'com.squareup.picasso:picasso:2.5.2'
compile 'com.melnykov:floatingactionbutton:1.3.0'
compile 'com.ogaclejapan.smarttablayout:library:1.1.3@aar'
compile 'com.ogaclejapan.smarttablayout:utils-v4:1.1.3@aar'
compile 'com.mcxiaoke.photoview:library:1.2.3'
compile 'com.pnikosis:materialish-progress:1.5'
compile('com.crashlytics.sdk.android:crashlytics:2.2.4@aar') { transitive = true; }
```

The **AppCompat** was used in order to make the app UI's look'n feel as much Material Design as possible, with the hold of **CardView**, **Materialish Progress** and **Floating Action Button**. The **RecyclerView** was used to handle all collections displayed in the app. All images loaded from the web were handled with **Picasso**, which allow us deal with asynchronous image downloading. Interfaces with tabs were build with **SmartTabLayout**. Fullscreen images are displayed with the **PhotoView** library, which allow users to zoom in/out and move around the image. The **Crashlytics** library is used to keep tracks of all non predictable crashes.

### Domain Layer
This layer have all business rules. All use-cases implementations, server-side communication and entity objects were implemented here. This layer doesn't know the existence of the presentation layer.

Third-party dependencies:
```
compile 'com.path:android-priority-jobqueue:1.1.2'
compile 'com.squareup.retrofit:retrofit:1.9.0'
```

The **Retrofit** library was used to do http calls. The **Priority Job Queue** was used to wrap all use cases in a thread-safe environment.

## Notes

- In order to solve the request search limit I called the search movie use case 1 second after the user typed in the search input.
- Since there were different image sizes, I displayed lower-sized images in my recylerviews and bigger or original sizes of images in the detailed screens.
- To pass data from the domain layer to the presentation layer I used a mapper to filter out all unnecessary information from the server-side that I wasn't going to use in the views.
- I used [this](https://gist.github.com/fada21/10655652) technique to cache images loaded from Picasso.


## Extra things I could have done

- An Image transition from the movie search screen to the movie detail screen.
- I didn't write tests. If I did I would test my presenters in the presentation layer and my use cases in the domain layer.

## References

- [The Clean Architecture by Uncle Bob](http://blog.8thlight.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Architecting Android…The clean way? by Fernando Cejas](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)

## Author
Thiago Rocha

+553184349266
