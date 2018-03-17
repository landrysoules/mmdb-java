# mmdb-java
mmdb java backend. Personnal project, model clone of Imdb.  
All data come from [https://www.themoviedb.org](www.themoviedb.org)

## Database
To start the database:
`docker run --rm --name mmdb-mongo -p 27017:27017 -v ~/Dev/dbstorage/mongo:/data/db:z  mongo`  

To manage it, compass community edition is a pretty cool choice :
[https://www.mongodb.com/download-center#compass]