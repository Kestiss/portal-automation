package browser

import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration

class DownloadDirectory {

    private static final Path DOWNLOAD_DIR = Path.of(System.getProperty('java.io.tmpdir'), 'portal-downloads')

    static String getAbsolutePath() {
        DOWNLOAD_DIR.toAbsolutePath().toString()
    }

    static void reset() {
        Files.createDirectories(DOWNLOAD_DIR)
        List<Path> paths = []
        Files.list(DOWNLOAD_DIR).withCloseable { stream ->
            paths.addAll(stream.toList())
        }
        paths.each { Files.deleteIfExists(it) }
    }

    static Set<String> snapshot() {
        Files.createDirectories(DOWNLOAD_DIR)
        Set<String> fileNames = [] as Set
        Files.list(DOWNLOAD_DIR).withCloseable { stream ->
            fileNames.addAll(stream
                    .filter { Files.isRegularFile(it) }
                    .map { it.fileName.toString() }
                    .toList())
        }
        fileNames
    }

    static Path waitForNewDownload(Set<String> previousFiles, Duration timeout = Duration.ofSeconds(60)) {
        long deadline = System.nanoTime() + timeout.toNanos()
        while (System.nanoTime() < deadline) {
            Path candidate = findCompletedDownload(previousFiles)
            if (candidate != null && isStable(candidate)) {
                return candidate
            }
            sleep(1000)
        }

        throw new AssertionError("No new completed download found in ${absolutePath} within ${timeout.seconds} seconds")
    }

    private static Path findCompletedDownload(Set<String> previousFiles) {
        List<Path> files = []
        Files.list(DOWNLOAD_DIR).withCloseable { stream ->
            files.addAll(stream
                    .filter { Files.isRegularFile(it) }
                    .filter { !previousFiles.contains(it.fileName.toString()) }
                    .filter { !it.fileName.toString().endsWith('.crdownload') }
                    .toList())
        }
        files ? files.first() : null
    }

    private static boolean isStable(Path file) {
        if (!Files.exists(file) || Files.size(file) == 0L) {
            return false
        }

        long firstSize = Files.size(file)
        sleep(1000)
        Files.exists(file) && Files.size(file) == firstSize
    }
}
