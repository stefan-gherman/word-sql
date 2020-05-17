# java_se-5th-world-sql-dojo

In this exercise you have to write the SQL statements which create
the tables in the proper structure.
There are tests written to ensure this, so you should check them.

Preparation
1. First, create a database on your computer named **world**.
2. Import the project with Intellij as a Maven project.
3. Set up a new configuration in Intellij through menu point
Run/Edit configurations as follows:
   1. Create a JUnit type of configuration since you want to run tests.
   2. Name your configuration as All Tests (or something like that - 
   if you leave it as unnamed it wont appear).
   3. Switch your Test kind to Class and select WorldDBCreatorTest class.\
      **In case of error message: "JUnit is not found in module 'world-sql-dojo'"**:\
      Open WorldDBCreatorTest.java in IDEA and click on the double green arrow in the line of the class.\
      This creates a run configuration on behalf of you and you can go through on it with these steps.
   4. Set up your database user and password as environmental
   variables in the Environmental variables section of that screen
   (use variable names POSTGRES_DB_USER and POSTGRES_DB_PASSWORD).

Now run the tests and they will surely fail (don't worry! :) ).
(If you have problems running the tests, make sure your 
Intellij installation is up-to-date.)

Write your SQL creational statements into the 
src/main/resources/init_db.sql!

