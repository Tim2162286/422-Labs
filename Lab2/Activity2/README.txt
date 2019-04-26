Set tomcat home and ports in build.properties. the deploy target will deploy to both tomcats, where the tomcat folder is ${tomcat.home}-${tomcat.port<x>}.
to deploy to one port, use the deployPort<x> target.
use the configuration.properties file in the properties folder to set which datastore type to use, and the information needed to set the datastore up.
for the Flat File store, it should work with the defaults.
for the database store, i used a MySql database named phonebook, running on localhost and using port 3306 by default, but this can be changed using the db.url property.
you will need to set the db.username and db.password properties to a valid login with admin access, since the application will create an initilize the database if it is not found, with a table name listings. if you want to manualy run the sql script, you can run the contents of the MySql.sql fount in the root of the source tree file in your database to create the entire database, or the MySqlCreate.sql file in the resourcesDynamic/data folder on a created database to create and initilize the table.
there is no schema validation. 