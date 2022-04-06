# File Archive Service

Single REST API endpoint that allows users to upload multiple files with a single request, archive them and return a single zip file.
System stores upload statistics: IP address and usage count per day in the database.

## Situations
1. To add multiple archiving methods (7z, for example);
   1. Simply create service that implements FileArchiver with specific functionality
2. If we face a significant increase in request count;
   1. Run file-archiver-service as a microservice with multiple replicas and load balancer
3. To allow 1GB max file size;
   1. Edit system properties:
      1. spring.servlet.multipart.max-file-size
      2. spring.servlet.multipart.max-request-size

## What needs to be fixed
1. Usage count per day. Currently, system logs total usage count

## What needs to be improved
1. Create request exception handler
