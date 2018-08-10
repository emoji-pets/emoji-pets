# EmojiPetz

### Goals and Motivations in Deciding to Do This:

- have fun making something cool
- have a project that could go in any direction, allowing us full creativity in applying what we are learning
- explore the "emoji problem" as a construct in society, breaking ground and treading upon new cultural insights into its meaning as we construct an app for it
- I through it out there as a last minute thought (Nick typing) and people voted on it, so we did it

### Functional inventory of this repo:

- full code for the front-end client of EmojiPetz, including:
  - 4 Activities
    - Sign in, Create Account, NavDrawer for User Stuff, Friend Activity for viewing a friend
  - Several fragments for those activities
  - Layout files and resources files
    - including a translation of the full string resources into Tagalog for use in the Philippines
- Javadocs
- Development documents, including ERDs, Wireframes, Etc.
- 3rd party resources including gson, retrofit, lottie, etc.

### The team:

![Justin, Lora, and Nick](docs/IMG_0444.JPG)

From left to right:

 - Justin Torrez (Salesforce, documentation, concept design) 
 - Lora Racca (Artistic direction, team leader, UI animation architect, Tagalog tranlsation) 
 - Nick LoCicero (Spring and AWS development, Play Store deployment, Front-end UI lead engineer)
 
### Current State:

No known bugs, it just needs to be tested on more phones.

### Platforms used:

- Java 7
- Android 27
- github.com/traex (RippleEffect Library)
- Gson
- Retrofit
- Lottie (airbnb.com)
- jUnit 4.12
- github.com/varunest(SparkButton 1.0.5)

### Cosmetic improvements

- simplify the UI interface, so things take up less real estate
- make it clean and sleek
  - things take up less space
  - have a pleasing contrast
  - glossy, vector image kind of look and feel

### Stretch goals:
    
- unfollow and block people
- notifications for when people write on wall
- track who writes on wall
- let pets lay eggs and implement some sort of game to find eggs your friends lay (geo location services needed for this)

### Wireframes and User-stories:

- please see the [docs](docs) folder

### ERD and DDL:

- please see [EmojiPetz Services](http://emoji-pets.github.io/emoji-pets-service)

### Javadoc:

- [link to javadoc is here](api/docs/index.html)

### Links to licenses:

- [our license](LICENSE) (listed in the top level of the repo, too)
- [gson](https://github.com/google/gson/blob/master/LICENSE)
- [retrofit](http://square.github.io/retrofit/)
- [lottie](https://github.com/airbnb/lottie-android/blob/master/LICENSE)
- [spark button](https://github.com/varunest/SparkButton/blob/master/LICENSE.md)
- [ripple effect](https://github.com/traex/RippleEffect/blob/master/LICENSE.txt)

### Building:

Please make sure Java is installed and then, using IntelliJ or Android Studio, import the project from Gradle.  Build the project and run on an emulator.  [Link to more information on how to build this with the Android toolset](https://developer.android.com/training/basics/firstapp/)

Or download from the Play Store for direct testing.

