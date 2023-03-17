# stock-service
springboot service for upload stocks


## Design goal
1. stock price history file could have record overlap, need handle this.

## implementation
1. file uploaded into a disk, different user have their own account name.
2. background parser process read the file in every 5 minutes
3. the parsed data will read into the redis server 

## Todo
1. test file upload
2. test scheduler
3. test file parser
4. test redis io