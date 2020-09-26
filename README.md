####Get Started
The project was built with Gradle and Spring Boot

To run the project use the following command from the root directory of the project:  

    ./gradlew run

####Trie Data Structure
The main goal since the beginning was to find the right data structure to handle the prefix search.  
After some study I came up with a particularization of a tree data structure called Trie.  
Each Trie node could represent a leaf of an intermediary node with multiple child paths.  

The concrete implementation to index prefixes is based on an char array.  
Each node has an array of size 10 (numbers 0 to 9) and each position can have a reference to another node.  
A filled array index with a node represents a value in the prefix path.  

                    O [null, child, null, ...]  
              |-----|-----|...  
             null   |    null  
                    |  
                    O [null, null, child, ...]  
              |-----|-----|...  
             null  null   |  
                          |  
                          O [null, null, null, ...]  

In the example presented we can index prefixes +1 and +12.  
The prefixes are matched for the biggest path matched.

####Project Architecture
The project is structured in three big layers (controller, service, repository).  
Controller is the application entry facade and depends on service abstractions.  
Service layer is where our business logic resides and depends on repository abstractions to access the outside world.  
Repository, from the DDD terminology, is where we define our components that will communicate with external resources (database, rest/soap web services, etc.).  
Other packages are provided for utilities and configurations.

The project relies mainly on abstractions and delegates Spring framework for implementations.

####RestTemplate and OpenFeign
Two flavors of the SectorApi contract are provided, one using Spring RestTemplate and another one using Netflix OpenFeign (being this the default one).  
The usage of OpenFeign with Hystrix provide us a Circuit Breaker pattern to handle failures.