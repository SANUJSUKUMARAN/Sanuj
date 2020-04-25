1.Clone the project from git
2.Right click on CRUDPETS.java and run as testng project
3.In case testng is not detected add it to the project via Eclipse Help-->Install New Software Url:https://dl.bintray.com/testng-team/testng-eclipse-release/6.14.3/
4.The config file contains the base uri in config.properties
5.Report is generated in the test-output folder
6.TestNG has been used as the framework

Cycle of test is
1.First add a pet to the store.Get the id(Use this as a dependency test so that using TestContext the id is passed to PUT operation)
2.Update the pet name as pigeon for the step 1 pet(Use this as depedency test for 3 and 4 so that the updated info is passed to 3 and 4)
3.Get the updated details of the pet
4.Delete the pet id