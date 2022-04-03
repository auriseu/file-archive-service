# file-upload-service

Single REST API endpoint that allows users to upload multiple files with a single request, archive them and return a single zip file.
System stores upload statistics: IP address and usage count per day in the database.

a. If we add multiple archiving methods (7z, for example);
b. Face a significant increase in request count;
c. Allow 1GB max file size;
