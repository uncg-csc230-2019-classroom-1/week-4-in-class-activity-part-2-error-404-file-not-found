*This was made for an assignment in a data structures and algorithms course at the University of North Carolina Greensboro*

### Usage

Basic Usage is to simply run the file

```
λ java -jar Babynames.jar
Enter The Year: 1923
Enter The Gender: F
Enter The name: Ellie
Ellie is ranked #802 in year 1923
```

There’s a slight delay between the prompt for the year and the prompt for the gender, during this delay the rankings are grabbed from [ssa.gov](https://ssa.gov), parsed, and serialized. 

Alternatively it can be run with command line arguments, the most basic of which is in the form `java -jar Babynames.jar [name] [gender]` this will then print the rankings of the given name from the years 1897-2017

```
λ java -jar BabyName.jar Ellie F
...
Ellie is ranked #360 in year 2000
Ellie is ranked #340 in year 2001
Ellie is ranked #200 in year 2002
Ellie is ranked #187 in year 2003
Ellie is ranked #189 in year 2004
Ellie is ranked #183 in year 2005
Ellie is ranked #175 in year 2006
Ellie is ranked #175 in year 2007
Ellie is ranked #167 in year 2008
Ellie is ranked #144 in year 2009
Ellie is ranked #104 in year 2010
...
```

There is also the option of specifying a year range like so

```
λ java -jar BabyName.jar Ellie F -y 2000-2010
Ellie is ranked #360 in year 2000
Ellie is ranked #340 in year 2001
Ellie is ranked #200 in year 2002
Ellie is ranked #187 in year 2003
Ellie is ranked #189 in year 2004
Ellie is ranked #183 in year 2005
Ellie is ranked #175 in year 2006
Ellie is ranked #175 in year 2007
Ellie is ranked #167 in year 2008
Ellie is ranked #144 in year 2009
Ellie is ranked #104 in year 2010
```

---

## TO DO

+ add option for reading names from file
+ add option to specify whether files should be saved