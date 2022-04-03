# File Archive Service

Single REST API endpoint that allows users to upload multiple files with a single request, archive them and return a single zip file.
System stores upload statistics: IP address and usage count per day in the database.

1. To add multiple archiving methods (7z, for example);
2. If we face a significant increase in request count;
3. To allow 1GB max file size;
