# SpringLint

[pt-br](README_pt.md)

SpringLint is a simple tool that performs code metrics and code smells analysis in your Spring MVC systems.
The tool is based on Maurício Aniche's PhD thesis. 

## Usage

```
java -jar springlint
 -o,--output <arg>    Path to the directory output.
 -otype <arg>         Type of the output: 'csv', 'html'
 -p,--project <arg>   Path to the project
```

Or if you like Maven:

```
mvn com.github.mauricioaniche:springlint-maven-plugin:0.5:springlint
```

Then, just check the generated html.

## Interpreting the code metrics

Springlint compares your classes to a benchmark of thousands of classes from 120 Spring MVC systems. 
The tool produces a treemap. Red dark squares mean your class is within the 10% worst classes (classes with highest values) in the benchmark. Light red squares (10-20%), yellow (20-30%), green (30%-100%) are the other categories. Also, the size of the square is proportional to the metric value: the bigger the square the higher the metric value. In practice, you should be worried about red classes.

Different from other tools, SpringLint uses different thresholds for each architectural role, e.g., if a class is a Controller, it uses [26,29,34] as thresholds for coupling, while it uses [16,20,25] for coupling in Entities. 

We use the following metrics from the CK suite:

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

## Interpreting the code smells

Springlint also detects different code smells in Spring MVC systems.  
Currently, we support 6 smells:

- **Promiscuous Controller**: When a Controller offers too many actions.

- **Brain Controller**: When a Controller offers too much flow control.

- **Meddling Service**: A Service that directly queries the database.

- **Brain repositories**: Complex logic in the Repositories.

- **Laborious Repository Method**: A method in a Repository that performs multiple actions 
with the database.

- **Fat Repository**: A Repository that manages too many entities.

Our studies show that classes affected by these smells are more prone to change and to defects. So, you need to avoid them.

## Related Work


* Alves, Tiago L., Christiaan Ypma, and Joost Visser. "Deriving metric thresholds from benchmark data." 
Software Maintenance (ICSM), 2010 IEEE International Conference on. IEEE, 2010.

* Chidamber, Shyam R., and Chris F. Kemerer. "A metrics suite for object oriented design." 
Software Engineering, IEEE Transactions on 20.6 (1994): 476-493.

## Author

Maurício Aniche (m.f.aniche at tudelft dot nl, @mauricioaniche)

*Collaborators (in no order):* 

- Christoph Treude
- Arie van Deursen
- Andy Zaidman
- Gabriele Bavota
- Marco Aurélio Gerosa

## License

This software is licensed under the Apache 2.0 License.
