# SpringLint

SpringLint is a simple tool that performs code metrics and code smells analysis in your Spring MVC systems.
The tool is based on Maurício Aniche's PhD thesis. 

# Usage

```
java -jar springlint
 -o,--output <arg>    Path to the output. Should be a dir ending with /
 -otype <arg>         Type of the output: 'csv', 'html'
 -p,--project <arg>   Path to the project
```

Or if you like Maven:

```
mvn com.github.mauricioaniche:springlint-maven-plugin:0.3:springlint
```

Then, just check the generated html.

# Author

Maurício Aniche (m.f.aniche at tudelft dot nl, @mauricioaniche)

*Collaborators (in no order):* 

- Christoph Treude
- Arie van Deursen
- Andy Zaidman
- Gabriele Bavota
- Marco Aurélio Gerosa

# License

This software is licensed under the Apache 2.0 License.
