package com.gmail.zavsek_o2;

import java.util.*;

class FileData {
    String fileName;
    long fileSize;
    String filePath;

    public FileData(String fileName, long fileSize, String filePath) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.filePath = filePath;
    }
}

class FileNavigator {
    Map<String, List<FileData>> fileMap;

    public FileNavigator() {
        fileMap = new HashMap<>();
    }

    public void add(String path, FileData file) {
        List<FileData> fileList = fileMap.getOrDefault(path, new ArrayList<>());
        fileList.add(file);
        fileMap.put(path, fileList);
    }

    public List<FileData> find(String path) {
        return fileMap.getOrDefault(path, new ArrayList<>());
    }

    public List<FileData> filterBySize(String path, long maxSize) {
        List<FileData> result = new ArrayList<>();
        List<FileData> fileList = fileMap.getOrDefault(path, new ArrayList<>());
        for (FileData file : fileList) {
            if (file.fileSize <= maxSize) {
                result.add(file);
            }
        }
        return result;
    }

    public void remove(String path) {
        fileMap.remove(path);
    }

    public List<FileData> sortBySize(String path) {
        List<FileData> fileList = fileMap.getOrDefault(path, new ArrayList<>());
        fileList.sort(Comparator.comparingLong(file -> file.fileSize));
        return fileList;
    }

    public void addConsistencyCheck(String path, FileData file) {
        if (!path.equals(file.filePath)) {
            System.out.println("Error: Path-key and file path do not match");
            return;
        }
        add(path, file);
    }
}

public class Main {
    public static void main(String[] args) {
        FileNavigator navigator = new FileNavigator();
        FileData file1 = new FileData("file1.txt", 100, "/path/to/file");
        FileData file2 = new FileData("file2.txt", 200, "/path/to/file");
        FileData file3 = new FileData("file3.txt", 150, "/path/to/another/file");

        navigator.add("/path/to/file", file1);
        navigator.add("/path/to/file", file2);
        navigator.addConsistencyCheck("/path/to/file", file3); // This will produce an error

        List<FileData> files = navigator.find("/path/to/file");
        for (FileData file : files) {
            System.out.println(file.fileName);
        }

        List<FileData> filteredFiles = navigator.filterBySize("/path/to/file", 200);
        for (FileData file : filteredFiles) {
            System.out.println(file.fileName);
        }

        navigator.remove("/path/to/file");

        List<FileData> sortedFiles = navigator.sortBySize("/path/to/file");
        for (FileData file : sortedFiles) {
            System.out.println(file.fileName);
        }
    }
}
