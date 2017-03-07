/*
 	Markedup Scraper - Java package for helping scraping data from websites and webservices.
 	Copyright (c) 2017 Rove Monteux
 
 	This file is part of Markedup Scraper.
 	
 	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

package cf.monteux.scraper.io;


	import java.io.BufferedInputStream;
	import java.io.BufferedOutputStream;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.io.OutputStream;
	import java.nio.channels.FileChannel;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.HashMap;
	import java.util.Scanner;

	/**
	 * Abstraction layer for disk I/O.
	 */
	public class FileIO {

		/**
		 * Reads the contents of a given file into a String.
		 * 
		 * @param filename	The file to read from
		 * @return	The String-encapsulated file contents
		 * @throws FileNotFoundException	An error indicating that the files does not exists
		 * 
		 * @see File
		 * @see Scanner
		 */
	    public static String read(String filename) throws FileNotFoundException {
	    	File file = new File(filename);
	        Scanner scanner = new Scanner(file);
	        scanner.useDelimiter("\\Z");
	        String result = scanner.next();
	        scanner.close();
	        scanner = null;
	        file = null;
	        return result;
	    }
	    
	    /**
	     * Writes a new file to disk or overwrite an existing file in disk.
	     * 
	     * @param filename	Name of the file to be written
	     * @param content	Data to be written to the file
	     * @throws IOException	Error while writing the file
	     */
	    public static void write(String filename, String content) throws IOException {
	        write(filename, content, false);
	    }

	    /**
	     * Writes a file to disk.
	     * 
	     * @param filename	Name of the file to be written
	     * @param content	Data to be written to the file
	     * @param append	Boolean indication if the file should be appended, or started anew
	     * @throws IOException	Error while writing the file
	     */
	    public static void write(String filename, String content, boolean append) throws IOException {
	        File file = new File(filename);
	        try {
	        file.getParentFile().mkdirs();
	        }
	        catch (Exception e) { 
	        	e.printStackTrace();
	        }
	        FileWriter fileWriter = new FileWriter(file, append);
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        bufferedWriter.write(content);
	        bufferedWriter.newLine();
	        bufferedWriter.close();
	        file = null;
	        bufferedWriter = null;
	        fileWriter = null;
	    }

	    /**
	     * Reads an object from disk.
	     * 
	     * @param filename	File name to read the object from
	     * @return	The object read from disk
	     * @throws FileNotFoundException	Error thrown if the file does not exists
	     * @throws IOException	Error while reading the file
	     * @throws ClassNotFoundException	Error while reading the object
	     */
	    public static Object readObject(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
	        BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(filename));
	        ObjectInputStream objectInput = new ObjectInputStream(fileInput);
	        Object objectResult =  objectInput.readObject();
	        objectInput.close();
	        objectInput = null;
	        fileInput = null;
	        return objectResult;
	    }

	    /**
	     * Persists an object to disk.
	     * 
	     * @param filename	File name to persist the object to
	     * @param objectContent	Object to be persisted
	     * @throws IOException	An input and output error
	     * 
	     * @see ObjectOutputStream
	     */
	    public static void writeObject(String filename, Object objectContent) throws IOException {
	        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(filename, false));
	        ObjectOutputStream objectOutput = new ObjectOutputStream(output);
	        objectOutput.writeObject(objectContent);
	        output.flush();
	        output.close();
	        output = null;
	        objectOutput = null;
	    }

	    /**
	     * Deletes a file in the file system.
	     * 
	     * @param filename	Filename to be deleted
	     * @return	Boolean indicating success or failure in the deletion
	     */
	    public static boolean delete(String filename) {
	        File file = new File(filename);
	        boolean result = file.delete();
	        file = null;
	        return result;
	    }

	    /**
	     * Checks if a file exists in the file system.
	     * 
	     * @param filename	The name of the file to check
	     * @return	a boolean indicating the existence of the given file
	     * 
	     * @see File
	     */
	    public static boolean exists(String filename) {
	        File file = new File(filename);
	        boolean result = file.exists();
	        file = null;
	        return result;
	    }

	    /**
	     * Delete a folder in the file system recursively.
	     * 
	     * @param directory	Folder to be deleted recursively
	     * @return	Boolean indicating success or failure in the deletion
	     */
	    public static boolean deleteFolder(File directory) {
	        if(directory.exists()) {
	            File[] files = directory.listFiles();
	            if(null!=files) {
	                for(int i=0; i<files.length; i++) {
	                    if(files[i].isDirectory()) {
	                        deleteFolder(files[i]);
	                    } else {
	                        files[i].delete();
	                    }
	                }
	            }
	            files = null;
	        }
	        return(directory.delete());
	    }

	    /**
	     * Returns a File object for a given path and file name.
	     * 
	     * @param path	Path for the File object
	     * @param filename	Name of the file to be returned
	     * @return	The File object
	     */
	    public static File getFile(String path, String filename) {
	    	if (path != null && path.length() > 0) {
	    		if (!(filename.contains(path))) {
	    			filename = path + filename;
	    		}
	    	}
	        return new File(filename);
	    }
	    
	    /**
	     * Writes data to a file in a specified path, and debug it to a running instance of the Log Framework.
	     * 
	     * @param path	Full path to write the file to
	     * @param filename	Name of the file to write the data to
	     * @param data	String-encapsulated data to be written to the specified file
	     * @throws IOException	An input and output error
	     * 
	     */
	    public static void write(String path, String filename, String data) throws IOException {
	        write(path, filename, data.getBytes("UTF-8"));
	    }

	    /**
	     * Writes data to a file in a specified path, and debug it to a running instance of the Log Framework.
	     * 
	     * @param path	Full path to write the file to
	     * @param filename	The file name
	     * @param data	Byte array-encapsulated data to be written to the specified file
	     * @throws IOException	An input and output error
	     * 
	     */
	    public static void write(String path, String filename, byte[] data) throws IOException {
	    	File file = getFile(path, filename);
	        file.getParentFile().mkdirs();
	        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
	        out.write(data);
	        out.flush();
	        out.close();
	        data = null;
	        filename = null;
	        out = null;
	        file = null;
	    }
	    
	    /**
	     * Creates a folder in the file system.
	     * 
	     * @param foldername	The name of the folder to be created
	     * 
	     * @see File
	     */
	    public static void createFolder(String foldername) {
	        File f = new File(foldername);
	        f.mkdirs();
	        f = null;
	    }

	    /**
	     * Copies folder contents from sourceFolderPath to destinationFolderPath recursively.
	     *
	     * @param sourceFolderPath	The source folder path
	     * @param destinationFolderPath	The destination folder path
	     * @throws IOException	An input and output error
	     */
	    public static void copyFolderRecursively(String sourceFolderPath, String destinationFolderPath) throws IOException {
	        File f = new File(sourceFolderPath);
	        if (!f.isDirectory()) return;
	        if (!destinationFolderPath.endsWith(File.separator)) destinationFolderPath += File.separator;

	        for (final File fileEntry : f.listFiles()) {
	            if (fileEntry.isDirectory()) {
	                copyFolderRecursively(fileEntry.getAbsolutePath(), destinationFolderPath + fileEntry.getName());
	            } else {
	                String fileString = read(fileEntry.getCanonicalPath());
	                write(destinationFolderPath + fileEntry.getName(), fileString);
	            }
	        }
	    }

	    /**
	     * 
	     * Reads the contents of a file into a String using a path.
	     * 
	     * @param path	Path for the file to be read
	     * @param filename	File name to read the data from
	     * @return	String containing the data from the given file
	     * @throws FileNotFoundException	Error thrown if the file does not exists
	     * @throws IOException	Error while reading the file
	     */
	    public static String read(String path, String filename) throws FileNotFoundException, IOException {
	        if (path != null && path.length() > 0) {
	        	if (!(filename.contains(path))) {
	        		if (!(path.endsWith(File.separator))) {
	        			path += File.separator;
	        		}
	        	filename = path + filename;
	        	}
	        }
	        String result = read(filename);
	        return result;
	    }

	    public static String getFilename(String filename) {
	        return new File(filename).getName();
	    }
	    
	    /**
	     * Copies a given file to another given file.
	     * 
	     * @param sourceFile	File to copy from
	     * @param destinationFile	File to copy to
	     * @return Boolean indicating the success or failure of the file copy
	     */
		public static boolean copy(String sourceFile, String destinationFile) {
			FileChannel source = null;
			FileChannel destination = null;
			FileInputStream sourceStream = null;
			FileOutputStream destinationStream = null;
			try {
				sourceStream = new FileInputStream(sourceFile);
				source = sourceStream.getChannel();
				destinationStream = new FileOutputStream(destinationFile);
				destination = destinationStream.getChannel();
				destination.transferFrom(source, 0, source.size());
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if (source != null) {
					try {
						source.close();
						sourceStream.close();
						source = null;
						sourceStream = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (destination != null) {
					try {
						destination.close();
						destinationStream.close();
						destination = null;
						destinationStream = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return true;
		}

		/**
		 * Gets the size of a file.
		 * 
		 * @param sourceFile	File to get the size from
		 * @return	File size
		 */
		public static long size(String sourceFile) {
			File file = new File(sourceFile);
			if (file.exists()) {
				long result = file.length();
				file = null;
				return result;
			}
			else {
				file = null;
				return 0;
			}
		}
		
		/**
		 * Returns the files in a given folder.
		 * 
		 * @param folder	Folder to return the files from
		 * @return	Files in a given folder
		 */
		public static ArrayList<File> list(String folder) {
			File file = new File(folder);
			ArrayList<File> files = null;
			try {
				files = new ArrayList<File>(Arrays.asList(file.listFiles()));
			}
			catch (NullPointerException e) {
				files = new ArrayList<File>();
			}
			file = null;
			folder = null;
			return files;
		}
		
		/**
		 * Returns the files of a given type in a given folder.
		 * 
		 * @param folder	Folder to return the files from
		 * @param extension	Extension indicating the file type to return
		 * @return	Files of a given type in a given folder
		 */
		public static ArrayList<File> list(String folder, String extension) {
			extension = extension.toLowerCase();
			ArrayList<File> files = new ArrayList<File>();
			for (File file: list(folder)) {
				if (file.getName().toLowerCase().endsWith("."+extension)) {
					files.add(file);
				}
			}
			folder = null;
			extension = null;
			return files;
		}
		
		/**
		 * Returns pairs of associated files according to association keywords, for example request and response.
		 * 
		 * @param folder	Folder containing the payload files		
		 * @param extension	File extension used by the payload files
		 * @param associationIn	Associated file identifier for the first item, for example request
		 * @param associationOut	Associated file identifier for the second item, for example response
		 * @return	Associated files of a given type in a given folder
		 */
		public static HashMap<File, File> associations(String folder, String extension, String associationIn, String associationOut) {
			extension = extension.toLowerCase();
			ArrayList<File> requestFiles = new ArrayList<File>();
			ArrayList<File> responseFiles = new ArrayList<File>();
			HashMap<File, File> associatedFiles = new HashMap<File, File>();
			for (File file: list(folder, extension)) {
				if (file.getName().toLowerCase().contains("_template_"+associationIn+"."+extension)) {
					requestFiles.add(file);
				}
				else if (file.getName().toLowerCase().contains("_template_"+associationOut+"."+extension)) {
					responseFiles.add(file);
				}
			}
			for (File requestFile: requestFiles) {
				for (File responseFile: responseFiles) {
					if (responseFile.getName().toLowerCase().replace("_"+associationOut+"."+extension, "").equals(requestFile.getName().toLowerCase().replace("_"+associationIn+"."+extension, ""))) {
						associatedFiles.put(requestFile, responseFile);
						break;
					}
				}
			}
			folder = null;
			extension = null;
			requestFiles = null;
			responseFiles = null;
			return associatedFiles;
		}
		
	    /**
	     * Wrapper for returning an InputStream for a given File.
	     * 
	     * This can later on be used to return the InputStream via a different Framework, for example Commons IO.
	     * 
	     * @param file	File to return the InputStream for
	     * @return	InputStream for the given File
	     * @throws FileNotFoundException	Error when the file is not found
	     */
	    public static InputStream inputstream(File file) throws FileNotFoundException {
	    	return new FileInputStream(file);
	    }
	    
	    /**
	     * Wrapper for returning an InputStream for a given file name.
	     * 
	     * This can later on be used to return the InputStream via a different Framework, for example Commons IO.
	     * 
	     * @param filename	File name to return the InputStream for
	     * @return	InputStream for the given file
	     * @throws FileNotFoundException
	     */
	    public static InputStream inputstream(String filename) throws FileNotFoundException {
	    	return inputstream(new File(filename));
	    }
	    
	    /**
	     * Wrapper for returning an OutputStream for a given File.
	     * 
	     * This can later on be used to return the OutputStream via a different Framework, for example Commons IO.
	     * 
	     * @param file	File to return the OutputStream for
	     * @return	OutputStream for the given File
	     * @throws FileNotFoundException	Error when the file is not found
	     */
	    public static OutputStream outputstream(File file) throws FileNotFoundException {
	    	return new FileOutputStream(file);
	    }
	    
	    /**
	     * Wrapper for returning an OutputStream for a given file name.
	     * 
	     * This can later on be used to return the OutputStream via a different Framework, for example Commons IO.
	     * 
	     * @param filename	File name to return the OutputStream for
	     * @return	OutputStream for the given file name
	     * @throws FileNotFoundException	Error when the file is not found
	     */
	    public static OutputStream outputstream(String filename) throws FileNotFoundException {
	    	return outputstream(new File(filename));
	    }
		
	}

