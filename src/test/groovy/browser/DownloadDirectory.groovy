package browser

import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration

class DownloadDirectory {

    private static final Path ROOT_DIR = Path.of(System.getProperty('java.io.tmpdir'), 'portal-downloads')

    private static Path threadDir() {
        ROOT_DIR.resolve(Thread.currentThread().id as String)
    }

    static String getAbsolutePath() {
        threadDir().toAbsolutePath().toString()
    }

    static void reset() {
        Path dir = threadDir()
        Files.createDirectories(dir)
        List<Path> paths = []
        Files.list(dir).withCloseable { stream ->
            paths.addAll(stream.toList())
        }
        paths.each { Files.deleteIfExists(it) }
    }

    static Set<String> snapshot() {
        Path dir = threadDir()
        Files.createDirectories(dir)
        Set<String> fileNames = [] as Set
        Files.list(dir).withCloseable { stream ->
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
            sleep(500)
        }

        throw new AssertionError("No new completed download found in ${absolutePath} within ${timeout.seconds} seconds")
    }

    private static Path findCompletedDownload(Set<String> previousFiles) {
        List<Path> files = []
        Files.list(threadDir()).withCloseable { stream ->
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
