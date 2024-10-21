## Application Overview ##

### 1. Core Components ###
GameProgress Class:
- This class represents the state of a game. It holds attributes like player progress, score, level, and other relevant data that define the game state.  
- It implements the Serializable interface to allow objects of this class to be serialized for saving to disk.  

### 2. Saving Module ###
Saving Class:
- Responsible for saving game progress to disk.  
- Contains methods to serialize GameProgress objects into files (saveGame method) and compress those files into a ZIP archive (zipFiles method).
- It uses ObjectOutputStream to write serialized data and ZipOutputStream to create the ZIP file.  
- Implements error handling to ensure any issues during file I/O operations are logged.  

### 3. Loading Module ###
Loading Class:
- Handles the extraction of saved game files from the ZIP archive and the deserialization of these files back into GameProgress objects.  
- Contains methods for unzipping files (openZip method) and reading serialized game data (openProgress method).  
- Similar to the saving module, it uses ObjectInputStream for deserialization and ZipInputStream for reading from ZIP archives, along with appropriate error handling.  

### 4. File Management ###
File Structure:
- The application maintains a specific directory structure, primarily located at D:\Games\savegames, where all game progress files and the ZIP archive are stored.  
- Each saved game is represented by a .dat file, and the compressed archive is a .zip file containing all saved games.  

### 5. Data Flow ###
Saving Process:
- Create GameProgress objects with the current state of the game.  
- Serialize these objects to .dat files in the specified directory.  
- Compress the .dat files into a single .zip file for easier storage and management.  
Loading Process:
- Extract the .dat files from the ZIP archive.  
- Deserialize the .dat files back into GameProgress objects.  
- Restore the game state using the deserialized objects.  

### 6. Error Handling ###
Both the saving and loading modules include comprehensive error handling mechanisms to manage potential issues 
during file I/O operations. Errors are logged to the console, providing feedback to the user about any issues encountered.