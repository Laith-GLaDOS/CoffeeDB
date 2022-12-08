# How the DB is driven
The DB listens on a TCP port, you can send requests/commands to that port and get responses  
First off, when you connect, you must send an AUTH command to authenticate and allow for usage of other commands  

# Commands
`AUTH <password>`: Authenticates with the DB  
`GETKEYS`: Gets all keys (must be authenticated to run this)  
`GET <key>`: Gets a key's value (must be authenticated to run this)  
`SET <key> <type> <value>`: Sets a key to a value (must be authenticated to run this)  
`DELETE <key>`: Deletes a key (must be authenticated to run this)  
`EXPIRE <key> IN <milliseconds>`: Makes a key expire (gets deleted when expires) after a certain amount of milliseconds (must be authenticated to run this)  

# Playing around
You can open a shell to run commands in the DB using Netcat with this command: `nc <host> <port>`

# Deploying
Make sure you have a working JDK install, and then clone this repository, and run these commands:
```bash
cd CoffeeDB/service/build
./build.sh
echo "<password>" > ./coffeedb_password
java -jar output/CoffeeDB.jar <port>
```
Replace `<password>` with the actual password you want for authentication in this database and replace `<port>` with the port you want the database to run on