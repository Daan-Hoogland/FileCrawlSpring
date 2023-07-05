package nl.digitalinvestigation.filecrawl.util;

import nl.digitalinvestigation.filecrawl.model.DirectoryHit;
import nl.digitalinvestigation.filecrawl.model.DirectoryHitJson;
import nl.digitalinvestigation.filecrawl.model.FileHit;
import nl.digitalinvestigation.filecrawl.model.FileHitJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverer {

    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

    private DateConverer() {
    }

    public static DirectoryHit generateUnixDate(DirectoryHit directoryHit, DirectoryHitJson directoryHitJson) {
        Date creationDate = new Date();
        try {
            creationDate.setTime(Long.parseLong(directoryHitJson.getCreationTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            creationDate = null;
        }
        directoryHit.setCreationTime(creationDate);

        Date lastAccessDate = new Date();
        try {
            lastAccessDate.setTime(Long.parseLong(directoryHitJson.getLastAccessTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            lastAccessDate = null;
        }
        directoryHit.setLastAccessTime(lastAccessDate);

        Date lastWriteDate = new Date();
        try {
            lastWriteDate.setTime(Long.parseLong(directoryHitJson.getLastWriteTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            lastWriteDate = null;
        }
        directoryHit.setLastWriteTime(lastWriteDate);

        return directoryHit;
    }

    public static FileHit generateUnixDate(FileHit fileHit, FileHitJson hit) {
        Date creationDate = new Date();
        try {
            creationDate.setTime(Long.parseLong(hit.getCreationTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            creationDate = null;
        }
        fileHit.setCreationTime(creationDate);

        Date lastAccessDate = new Date();
        try {
            lastAccessDate.setTime(Long.parseLong(hit.getLastAccessTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            lastAccessDate = null;
        }
        fileHit.setLastAccessTime(lastAccessDate);

        Date lastWriteDate = new Date();
        try {
            lastWriteDate.setTime(Long.parseLong(hit.getLastWriteTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            lastWriteDate = null;
        }
        fileHit.setLastWriteTime(lastWriteDate);

        Date directoryCreationTime = new Date();
        try {
            directoryCreationTime.setTime(Long.parseLong(hit.getDirectoryHitJson().getCreationTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            directoryCreationTime = null;
        }
        fileHit.getDirectoryHit().setCreationTime(directoryCreationTime);

        Date directoryLastAccessTime = new Date();
        try {
            directoryLastAccessTime.setTime(Long.parseLong(hit.getDirectoryHitJson().getLastAccessTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            directoryLastAccessTime = null;
        }
        fileHit.getDirectoryHit().setLastAccessTime(directoryLastAccessTime);

        Date directoryLastWriteTime = new Date();
        try {
            directoryLastWriteTime.setTime(Long.parseLong(hit.getDirectoryHitJson().getLastWriteTime().replaceAll("\\D+", "")) * 1000L);
        } catch (NumberFormatException e) {
            directoryLastWriteTime = null;
        }
        fileHit.getDirectoryHit().setLastWriteTime(directoryLastWriteTime);

        return fileHit;
    }

    public static DirectoryHit generateGenericDate(DirectoryHit directoryHit, DirectoryHitJson directoryHitJson) {
        Date creationDate = new Date();
        creationDate.setTime(Long.parseLong(directoryHitJson.getCreationTime().replaceAll("\\D+", "")));
        directoryHit.setCreationTime(creationDate);

        Date lastAccessDate = new Date();
        lastAccessDate.setTime(Long.parseLong(directoryHitJson.getLastAccessTime().replaceAll("\\D+", "")));
        directoryHit.setLastAccessTime(lastAccessDate);

        Date lastWriteDate = new Date();
        lastWriteDate.setTime(Long.parseLong(directoryHitJson.getLastWriteTime().replaceAll("\\D+", "")));
        directoryHit.setLastWriteTime(lastWriteDate);

        return directoryHit;
    }

    public static FileHit generateGenericDate(FileHit fileHit, FileHitJson hit) {
        Date creationDate = new Date();
        creationDate.setTime(Long.parseLong(hit.getCreationTime().replaceAll("\\D+", "")));
        fileHit.setCreationTime(creationDate);

        Date lastAccessDate = new Date();
        lastAccessDate.setTime(Long.parseLong(hit.getLastAccessTime().replaceAll("\\D+", "")));
        fileHit.setLastAccessTime(lastAccessDate);

        Date lastWriteDate = new Date();
        lastWriteDate.setTime(Long.parseLong(hit.getLastWriteTime().replaceAll("\\D+", "")));
        fileHit.setLastWriteTime(lastWriteDate);

        Date directoryCreationTime = new Date();
        directoryCreationTime.setTime(Long.parseLong(hit.getDirectoryHitJson().getCreationTime().replaceAll("\\D+", "")));
        fileHit.getDirectoryHit().setCreationTime(directoryCreationTime);

        Date directoryLastAccessTime = new Date();
        directoryLastAccessTime.setTime(Long.parseLong(hit.getDirectoryHitJson().getLastAccessTime().replaceAll("\\D+", "")));
        fileHit.getDirectoryHit().setLastAccessTime(directoryLastAccessTime);

        Date directoryLastWriteTime = new Date();
        directoryLastWriteTime.setTime(Long.parseLong(hit.getDirectoryHitJson().getLastWriteTime().replaceAll("\\D+", "")));
        fileHit.getDirectoryHit().setLastWriteTime(directoryLastWriteTime);

        return fileHit;
    }

    public static DirectoryHit generateWindowsDate(DirectoryHit directoryHit, DirectoryHitJson directoryHitJson) throws ParseException {
        directoryHit.setCreationTime(formatter.parse(directoryHitJson.getCreationTime()));
        directoryHit.setLastAccessTime(formatter.parse(directoryHitJson.getLastAccessTime()));
        directoryHit.setLastWriteTime(formatter.parse(directoryHitJson.getLastWriteTime()));

        return directoryHit;
    }

    public static FileHit generateWindowsDate(FileHit fileHit, FileHitJson fileHitJson) throws ParseException {
        fileHit.setCreationTime(formatter.parse(fileHitJson.getCreationTime()));
        fileHit.setLastAccessTime(formatter.parse(fileHitJson.getLastAccessTime()));
        fileHit.setLastWriteTime(formatter.parse(fileHitJson.getLastWriteTime()));

        fileHit.getDirectoryHit().setCreationTime(formatter.parse(fileHitJson.getDirectoryHitJson().getCreationTime()));
        fileHit.getDirectoryHit().setLastAccessTime(formatter.parse(fileHitJson.getDirectoryHitJson().getLastAccessTime()));
        fileHit.getDirectoryHit().setLastWriteTime(formatter.parse(fileHitJson.getDirectoryHitJson().getLastWriteTime()));

        return fileHit;
    }

    public static Date convertWindowsToDate(String date) throws ParseException {
        return formatter.parse(date);
    }
}
