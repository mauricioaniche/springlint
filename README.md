# SpringLint

SpringLint is a simple tool that detects smells in your Spring MVC systems.
The tool is based on Maurício Aniche's PhD thesis. 

# Usage

```
usage: java -jar springlint
 -o,--output <arg>    Path to the output. Should be a dir ending with /
 -otype <arg>         Type of the output: 'csv', 'html'
 -p,--project <arg>   Path to the project
```

Or if you like Maven:

```
mvn com.github.mauricioaniche:springlint-maven-plugin:0.3:springlint
```

# How to interpret?

SmellyCat produces a treemap. It means that the higher the class' square,
the higher the metric value. However, it classifies the value in 4
categories. Green means that your class is below any risk limit. It means
that, to the point of view of that metric, you should not be worried.
Yellow means moderate risk. It means that you should start taking
care of it. Light red means high risk, and dark red means
very high risk. These are the classes you should be worried about, as
they are very far from what we consider "good".

SpringLint uses different thresholds for each architectural role,
e.g., if a class is a Controller, it uses [26,29,34] as thresholds for
coupling, while it uses [16,20,25] for coupling in Entities. Numbers
were derived from an empirical study in 120 Spring MVC systems.

## CK metrics

SmellyCat analyses CK metrics in your architectural roles and
uses Alves' et al approach to find moderate, high, and very high
risk classes. 

We use the following metrics:

- **Weighted Methods Per Class (WMC)**. In a simple way,
it is the sum of each method's complexity in a class. It is usually represented
by the Number of McCabe, which is the number of different possible paths of execution in a method. 

- **Number of Methods (NOM)**. The number of methods in a class
is also another common metric to explain the complexity of a class. Some tools
even use NOM instead of McCabe's Number to represent WMC. 

- **Coupling Between Object Classes (CBO)**. The number of 
classes a class depends upon. It counts classes used from both external
libraries as well as classes from the project.

- **Response for a Class (RFC)**. It is the count of 
all method invocations that happen in a class. 

- **Lack of Cohesion of Methods (LCOM)**. As the name suggests,
it measures the lack of cohesion in a class. The idea behind the metric is to count
the number of intersections between methods and attributes. The higher
the number, the less cohesive is the class.

## Code Smells

We propose 6 smells for Spring MVC applications. More details about them
can be found in our paper.

- **Promiscuous Controller**: When a Controller offers too many actions.

- **Smart Controller**: When a Controller offers too much flow control.

- **Meddling Service**: A Service that directly queries the database.

- **Smart repositories**: Complex logic in the Repositories.

- **Laborious Repository Method**: A method in a Repository that performs multiple actions 
with the database.

- **Fat Repository**: A Repository that manages too many entities.

# Publications

TODO: list of publications here

# References

* Alves, Tiago L., Christiaan Ypma, and Joost Visser. "Deriving metric thresholds from benchmark data." 
Software Maintenance (ICSM), 2010 IEEE International Conference on. IEEE, 2010.

* Chidamber, Shyam R., and Chris F. Kemerer. "A metrics suite for object oriented design." 
Software Engineering, IEEE Transactions on 20.6 (1994): 476-493.

# Author

Maurício Aniche (m.f.aniche at tudelft dot nl)

# License

This software is licensed under the Apache 2.0 License.
