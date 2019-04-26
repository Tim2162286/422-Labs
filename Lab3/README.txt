All properties files are in the properties folder.
Before starting the server you will need to adjust the tomcat home property in build.properties.
You will also need to set the database username and password properties in rdbm.properties.
Before running the application, you will need to set up the database, using either sql.file in the source tree. the MySqlCreate will prepopulate the DB with some data, the MySqlCreate_NoData will not.
if you use the no data version, you will need to add books to the books table. i have added an additional post endpoint to "/phonebook/:book" to allow you to do this in the api.
entries are created unlisted, and can only be added to an existing phonebook. attempting to add to a non existent phonebook will throw an error.
the application will not create the database if it does not exist. the sql statements i provided should create a unique database matching the url in rdbm.properties.
Do not modify the configuration.properties file. the only property it contains is a datastore type, but only a MySQL database is currently implemented
