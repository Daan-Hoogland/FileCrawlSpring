package nl.digitalinvestigation.filecrawl.util;

import nl.digitalinvestigation.filecrawl.constant.ScriptBody;
import nl.digitalinvestigation.filecrawl.model.Query;

import java.util.Arrays;
import java.util.List;

public class QueryBuilder {

    private QueryBuilder() {
    }

    public static String buildUnixQuery(List<Query> queries) {
        StringBuilder builder = new StringBuilder();

        builder.append(ScriptBody.BASH_HEADER);

        for (int i = 0; i < queries.size(); i++) {
            Query query = queries.get(i);
            String[] paths = null;
            if (query.getPaths() != null && !query.getPaths().isEmpty()) {
                if (query.getPaths() != null && !query.getPaths().isEmpty()) {
                    if (query.getPaths().contains(";")) {
                        paths = query.getPaths().split(";");
                    } else if (query.getPaths().contains(",")) {
                        paths = query.getPaths().split(",");
                    } else {
                        paths = new String[]{query.getPaths()};
                    }
                }
            }

            String[] names = null;
            if (query.getNames() != null && !query.getNames().isEmpty()) {
                if (query.getNames().contains(";")) {
                    names = query.getNames().split(";");
                } else if (query.getNames().contains(",")) {
                    names = query.getNames().split(",");
                } else {
                    names = new String[]{query.getNames()};
                }
            }

            if (paths != null) {
                for (int pathIndex = 0; pathIndex < paths.length; pathIndex++) {
                    if (names != null) {
                        for (int nameIndex = 0; nameIndex < names.length; nameIndex++) {

                            StringBuilder command = new StringBuilder(String.format(ScriptBody.BASH_COMMAND, getTargetUrl(query) + "?platform=unix", query.getId(), getExecutedUrl(query, "UNIX")));
                            command.append(String.format(ScriptBody.BASH_PATH, paths[pathIndex]));
                            command.append(String.format(ScriptBody.BASH_NAME, names[nameIndex]));
                            if (query.getSize() != null) {
                                command.append(String.format(ScriptBody.BASH_SIZE, query.getSize()));
                            }
                            if (query.getHash() != null && !query.getHash().isEmpty()) {
                                command.append(String.format(ScriptBody.BASH_HASH, query.getHash()));
                                if (query.getHashAlgorithm() != null && !query.getHashAlgorithm().isEmpty()) {
                                    command.append(String.format(ScriptBody.BASH_HASH_ALGORITHM, query.getHashAlgorithm()));
                                }
                            }
                            command.append("\n");
                            builder.append(command);
                        }
                    } else {
                        StringBuilder command = builder.append(String.format(ScriptBody.BASH_COMMAND, getTargetUrl(query), query.getId(), getExecutedUrl(query, "UNIX")));
                        command.append(String.format(ScriptBody.BASH_PATH, paths[pathIndex]));
                        if (query.getSize() != null) {
                            command.append(String.format(ScriptBody.BASH_SIZE, query.getSize()));
                        }
                        if (query.getHash() != null) {
                            command.append(String.format(ScriptBody.BASH_HASH, query.getHash()));
                            if (query.getHashAlgorithm() != null && !query.getHashAlgorithm().isEmpty()) {
                                command.append(String.format(ScriptBody.BASH_HASH_ALGORITHM, query.getHashAlgorithm()));
                            }
                        }
                        command.append("\n");
                    }
                }
            } else if (names != null) {
                for (int nameIndex = 0; nameIndex < names.length; nameIndex++) {
                    StringBuilder command = builder.append(String.format(ScriptBody.BASH_COMMAND, getTargetUrl(query), query.getId(), getExecutedUrl(query, "UNIX")));
                    command.append(String.format(ScriptBody.BASH_NAME, names[nameIndex]));
                    if (query.getSize() != null) {
                        command.append(String.format(ScriptBody.BASH_SIZE, query.getSize()));
                    }
                    if (query.getHash() != null) {
                        command.append(String.format(ScriptBody.BASH_HASH, query.getHash()));
                        if (query.getHashAlgorithm() != null && !query.getHashAlgorithm().isEmpty()) {
                            command.append(String.format(ScriptBody.BASH_HASH_ALGORITHM, query.getHashAlgorithm()));
                        }
                    }
                    command.append("\n");
                }
            } else {
                StringBuilder command = builder.append(String.format(ScriptBody.BASH_COMMAND, getTargetUrl(query), query.getId(), getExecutedUrl(query, "UNIX")));
                if (query.getSize() != null) {
                    command.append(String.format(ScriptBody.BASH_SIZE, query.getSize()));
                }
                if (query.getHash() != null) {
                    command.append(String.format(ScriptBody.BASH_HASH, query.getHash()));
                    if (query.getHashAlgorithm() != null && !query.getHashAlgorithm().isEmpty()) {
                        command.append(String.format(ScriptBody.BASH_HASH_ALGORITHM, query.getHashAlgorithm()));
                    }
                }
                command.append("\n");
            }
        }

        return builder.toString();

    }

    public static String buildWindowsQuery(List<Query> queries) {
        StringBuilder builder = new StringBuilder();


        for (int i = 0; i < queries.size(); i++) {
            Query query = queries.get(i);

            if (queries.size() > 1) {
                builder.append(ScriptBody.POWERSHELL_QUEUE);
            }

            String url = getTargetUrl(query);
            url = "\"" + url + "?platform=win`\"&`\"query=" + query.getId() + "\"";

            builder.append(String.format(ScriptBody.POWERSHELL_COMMAND, url, query.getId(), "\"" + getExecutedUrl(query, "WIN") + "\""));
            System.out.println(url);

            if (query.getPaths() != null && !query.getPaths().isEmpty()) {
                if (query.getPaths().contains(";")) {
                    String[] paths = query.getPaths().split(";");
                    String[] pathsTrimmed = Arrays.stream(paths).map(String::trim).toArray(String[]::new);
                    String pathString = "\'" + String.join("\', \'", pathsTrimmed) + "\'";
                    builder.append(String.format(ScriptBody.POWERSHELL_PATH, pathString));
                } else if (query.getPaths().contains(",")) {
                    String[] paths = query.getPaths().split(",");
                    String[] pathsTrimmed = Arrays.stream(paths).map(String::trim).toArray(String[]::new);
                    String pathString = "\'" + String.join("\', \'", pathsTrimmed) + "\'";
                    builder.append(String.format(ScriptBody.POWERSHELL_PATH, pathString));
                } else {
                    builder.append(String.format(ScriptBody.POWERSHELL_PATH, "\'" + query.getPaths().trim() + "\'"));
                }
            }

            if (query.getNames() != null && !query.getNames().isEmpty()) {
                if (query.getNames().contains(";")) {
                    String[] names = query.getNames().split(";");
                    String[] namesTrimmed = Arrays.stream(names).map(String::trim).toArray(String[]::new);
                    String nameString = "\'" + String.join("\', \'", namesTrimmed) + "\'";
                    builder.append(String.format(ScriptBody.POWERSHELL_NAME, nameString));
                } else if (query.getNames().contains(",")) {
                    String[] names = query.getNames().split(",");
                    String[] namesTrimmed = Arrays.stream(names).map(String::trim).toArray(String[]::new);
                    String nameString = "\'" + String.join("\', \'", namesTrimmed) + "\'";
                    builder.append(String.format(ScriptBody.POWERSHELL_NAME, nameString));
                } else {
                    builder.append(String.format(ScriptBody.POWERSHELL_NAME, "\'" + query.getNames().trim() + "\'"));
                }
            }

            if (query.getHash() != null && !query.getHash().isEmpty()) {
                builder.append(String.format(ScriptBody.POWERSHELL_HASH, query.getHash()));
            }

            if (query.getHashAlgorithm() != null && !query.getHashAlgorithm().isEmpty()) {
                builder.append(String.format(ScriptBody.POWERSHELL_HASH_ALGORITHM, query.getHashAlgorithm()));
            }

            if (query.getSize() != null && !(query.getSize() == 0)) {
                builder.append(String.format(ScriptBody.POWERSHELL_SIZE, query.getSize()));
            }

            if (i != queries.size() - 1) {
                builder.append("\n");
            }

        }

        return builder.toString();
    }


    public static String buildJavaQuery(List<Query> queries, String platform) {
        StringBuilder builder = new StringBuilder();

        switch (platform.toUpperCase()) {
            case "PS":
                for (Query query : queries) {
                    builder.append(ScriptBody.POWERSHELL_JAVA);

                    String arguments = buildJavaArgs(new StringBuilder(), query).toString();
                    arguments = String.format("'-jar JavaCrawler.jar -q %d -t %s %s'", query.getId(), getTargetUrl(query), arguments);
                    builder.append(arguments).append("\n");
                }
                break;
            case "BATCH":
                builder.append(ScriptBody.BATCH_HEADER);
                for (Query query : queries) {
                    builder.append(String.format(ScriptBody.JAVA_COMMAND, getTargetUrl(query), query.getId()));

                    builder = buildJavaArgs(builder, query);
                    builder.append("\n");
                }
                break;
            case "BASH":
                builder.append(ScriptBody.BASH_HEADER);

                for (int i = 0; i < queries.size(); i++) {
                    Query query = queries.get(i);

                    if (i > 0) {
                        builder.append(ScriptBody.BASH_QUEUE_REST);
                    }

                    builder.append(String.format(ScriptBody.JAVA_COMMAND, getTargetUrl(query), query.getId()));

                    builder = buildJavaArgs(builder, query);

                    if (i != queries.size() - 1) {
                        builder.append(ScriptBody.BASH_QUEUE);
                    }

                }
                break;
        }

        return builder.toString();
    }

    private static StringBuilder buildJavaArgs(StringBuilder builder, Query query) {
        if (query.getPaths() != null && !query.getPaths().isEmpty()) {
            if (query.getPaths().contains(";")) {
                String[] paths = query.getPaths().split(";");
                String pathString = String.join(" ", paths);
                builder.append(String.format(ScriptBody.JAVA_PATH, pathString));
            } else if (query.getPaths().contains(",")) {
                String[] paths = query.getPaths().split(",");
                String pathString = String.join(" ", paths);
                builder.append(String.format(ScriptBody.JAVA_PATH, pathString));
            } else if (query.getPaths().contains(" ")) {
                builder.append(String.format(ScriptBody.JAVA_PATH, query.getPaths()));
            } else {
                builder.append(String.format(ScriptBody.JAVA_PATH, query.getPaths()));
            }
        }

        if (query.getNames() != null && !query.getNames().isEmpty()) {
            String getNames = query.getNames().replace("*", ".*");
            if (query.getNames().contains(";")) {
                String[] names = getNames.split(";");
                String nameString = String.join(" ", names);
                builder.append(String.format(ScriptBody.JAVA_NAME, nameString));
            } else if (query.getNames().contains(",")) {
                String[] names = getNames.split(",");
                String nameString = String.join(" ", names);
                builder.append(String.format(ScriptBody.JAVA_NAME, nameString));
            } else if (query.getNames().contains(" ")) {
                builder.append(String.format(ScriptBody.JAVA_NAME, getNames));
            } else {
                builder.append(String.format(ScriptBody.JAVA_NAME, getNames));
            }
        }

        if (query.getHash() != null && !query.getHash().isEmpty()) {
            builder.append(String.format(ScriptBody.JAVA_HASH, query.getHash()));
        }

        if (query.getSize() != null && !(query.getSize() == 0)) {
            builder.append(String.format(ScriptBody.JAVA_SIZE, query.getSize()));
        }

        return builder;
    }

    private static String getTargetUrl(Query query) {
        String target = "";
        switch (query.getType()) {
            case FILE:
                target = query.getProject().getTarget() + ScriptBody.FILE_ENDPOINT;
                break;
            case DIRECTORY:
                target = query.getProject().getTarget() + ScriptBody.DIRECTORY_ENDPOINT;
                break;
        }

        return target;
    }

    private static String getExecutedUrl(Query query, String platform) {
        String target = query.getProject().getTarget() + "/hits/execute/create?query=" + query.getId();

        switch (platform.toUpperCase()) {
            case "WIN":
                target += "`\"&`\"platform=win";
                break;
            case "UNIX":
                target += "\\&platform=unix";
                break;
            case "JAVA":
                target += "\\&platform=java";
                break;
        }

        return target;
    }
}
