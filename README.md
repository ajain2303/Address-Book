# Address Book API
Built a RESTful API in Java for an Address Book with an Elasticearch Data Storage. 

# Capabilities
- GET: Get a list of all contacts or get a contact by name
- POST: Create a contact
- PUT: Update a contact
- DELETE: Delete a contact

# Install and Run Elastic Search 
curl -L -O https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.2.4.tar.gz
tar -xvf elasticsearch-6.2.4.tar.gz
cd elasticsearch-6.2.4/bin
./elasticsearch
We now have the Elastic search up and runnning.

# How to run the code
Once you have Elastic search server running, open up the repository in an IDE. Once you click run, you should be able to acces http://localhost:4567
This is where we are going to making all of our RESTful requests to the Address Book. 

GET: To view all contacts in the Address Book, send a GET request to http://localhost:4567/contact. To view a contact for a specific name send a GET request to the same address with the name appended to it as such: http://localhost:4567/contact/{name}

POST: To create a contact, send a POST request to http://localhost:4567/contact with a request body that looks like this: 
{
 	"name": "Bob",
 	"phone":"1234567890",
  "city": "Sydney",
  "state":"AU",
  "streetAddress":"42 Wallaby Way"
 }
 
 PUT: To update a contact, send a request to http://localhost:4567/contact/{name} with a request body of all the fields desired to change
 
 DELETE: To delete a contact, send a DELETE request to http://localhost:4567/contact/{name} with the name of the contact you want to delete

