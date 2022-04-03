package com.aurimas.demo.util;

public enum ArchiveMethod {
    ZIP(Constants.EXT_ZIP),
    SEVENZ(Constants.EXT_7Z);

    private final String extension;

    ArchiveMethod(String extension) {
        this.extension = extension;
    }

    public static ArchiveMethod fromExtension(String extension) {
        switch (extension) {
            case Constants.EXT_ZIP:
                return ZIP;
            case Constants.EXT_7Z:
                return SEVENZ;
            default:
                throw new IllegalArgumentException(extension + " is not supported");
        }
    }

    public String getExtension() {
        return extension;
    }

    private static class Constants {
        public static final String EXT_ZIP = "zip";
        public static final String EXT_7Z = "7z";
    }
}
