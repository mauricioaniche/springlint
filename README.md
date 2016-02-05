# SmellyCat

SmellyCat is a simple tool that detects smells in your classes.
The tool is based on Maurício Aniche's PhD thesis. 

Currently, we only support two architectures: Spring MVC and Android.
In Spring MVC, we evaluate the quality of your `Controllers`, 
`Services`, `Components`,`Repositories`, and `Entities`.
In Android, we evaluate the quality of your `Activity`, `Task`,
and `Fragment`.

Examples of the analysis: [CK in SSP (Spring MVC)](http://mauricioaniche.github.io/smellycat/ssp.html),
[CK in AnyMemo (Android)](http://mauricioaniche.github.io/smellycat/anymemo.html).

# Analysis

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

Still working on it...

# Usage

```
usage: java -jar smellycat
 -a,--analysis <arg>   Type of the analysis ('ck', 'smell')
 -arch <arg>           Architecture ('springmvc', 'android')
 -o,--output <arg>     Path to the output. It should end with '.html'
 -p,--project <arg>    Path to the project
```

# Publications

TODO: list of publications here

TODO: how to cite?

# References

* Alves, Tiago L., Christiaan Ypma, and Joost Visser. "Deriving metric thresholds from benchmark data." 
Software Maintenance (ICSM), 2010 IEEE International Conference on. IEEE, 2010.

* Chidamber, Shyam R., and Chris F. Kemerer. "A metrics suite for object oriented design." 
Software Engineering, IEEE Transactions on 20.6 (1994): 476-493.

* Aniche, Maurício F., Gustavo A. Oliva, and Marco A. Gerosa. "Are the methods in your 
data access objects (DAOs) in the right place? A preliminary study." Managing Technical Debt (MTD), 
2014 Sixth International Workshop on. IEEE, 2014.

# License

This software is licensed under the Apache 2.0 License.
