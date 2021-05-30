<h1 align="center">
  <br>
  <a href="https://github.com/sopra-fs21-group13"><img src="src/main/assets/flashy_h-green.svg" alt="Flashy" width="500"></a>
  <br>
  Flashy-Server
  <br>
</h1>


<p align="center">
  <a href="https://github.com/sopra-fs21-group-13/Server/actions">
    <img src="https://github.com/sopra-fs21-group-13/Server/workflows/Deploy%20Project/badge.svg">
  </a>
  <a href="https://sonarcloud.io/dashboard?id=sopra-fs21-group-13_Server">
      <img src="https://sonarcloud.io/api/project_badges/measure?project=sopra-fs21-group-13_Server&metric=coverage">
  </a>
</p>



## Introduction

Flashy is an application with which students (and other users) can create digital fashcard stacks. Besides the ordinary capabilities of physical fashcards, the ability to share the created stacks with other users, promoting the learning experience using social interaction and creating a good working environment.

A game component is also introduced, such that multiple users can participate competing against each other; seeing who can solve the stacks the fastest. We want to include a feature which captures the user's advancement towards manifesting their knowledge such as a reward system or experience/level type of thing.

## Technologies (short)

The server is written in **Java** using the **Spring Boot** framework. **JPA** is used for persistence and deployment is handled by **Heroku**. To establish a connection between the front- and backend **REST** is used for all the activities.

## High-level Components

The strucutre is as follow: controller, service and repository classes. The client side can contact the backend via API calls which are then handled by the controller. The controller further processes the call and uses the services and the repositories to fulfill the API call. The entity will be saved to the database with the JPA-Repositories, which in combination with Hibernate enable the flow to be effortless. 

## Launch and Deploy

You can use the local Gradle Wrapper to build the application.

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```

## Authors & Acknowledgment

- [Kiram Ben Aleya](https://github.com/SoftwareConstructionGroup)
- [Seonbin Kim](https://github.com/seonbinnn) 
- [Remus Nichiteanu](https://github.com/rnichi1) 
- [Nazim Bayram](https://github.com/NazimBayram)
- [Silvan Caduff](https://github.com/sicadu)

We would like to extend our thanks to anyone who has supported us through this challenging  project. Furthermore, we want to thank our TA [Jan Willi](https://github.com/JaanWilli) who's advice was very valuable to us. 

## Roadmap

1. Extend mulitplayer functionality (more than 2 players)
2. Save pictures with the sets
3. Add vanilla sets to the interface.


## License

Copyright (c) 2021 Flashy.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
