package browser

import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration

class DownloadDirectory {

    private static final Path ROOT_DIR = Path.of(System.getProperty('java.io.tmpdir'), 'portal-downloads')
    private static final Set<String> IN_PROGRESS_EXTENSIONS = ['.crdownload', '.part'] as Set

    private static Path ensureThreadDir() {
        Files.createDirectories(ROOT_DIR.resolve(Thread.currentThread() as String))
    }

    static String getAbsolutePath() {
        ensureThreadDir().toString()
    }

    static void reset() {
        Path dir = ensureThreadDir()
        Files.list(dir).withCloseable { stream ->
            stream.filter { Files.isRegularFile(it) }.forEach { Files.deleteIfExists(it) }
        }
    }

    static Set<String> snapshot() {
        Set<String> names = [] as Set
        Files.list(ensureThreadDir()).withCloseable { stream ->
            names.addAll(stream.filter { Files.isRegularFile(it) }.map { it.fileName.toString() }.toList())
        }
        names
    }

    static Path waitForNewDownload(Set<String> previousFiles, Duration timeout = Duration.ofSeconds(60)) {
        long deadline = System.nanoTime() + timeout.toNanos()
        while (System.nanoTime() < deadline) {
            Path candidate = findCompletedDownload(previousFiles)
            if (candidate != null && isStable(candidate)) {
                return candidate
            }
        }
        throw new AssertionError("No new completed download found in ${absolutePath} within ${timeout.seconds} seconds")
    }

    private static Path findCompletedDownload(Set<String> previousFiles) {
        List<Path> files = []
        Files.list(ensureThreadDir()).withCloseable { stream ->
            files.addAll(stream
                    .filter { Files.isRegularFile(it) }
                    .filter { !previousFiles.contains(it.fileName.toString()) }
                    .filter { !IN_PROGRESS_EXTENSIONS.any { ext -> it.fileName.toString().endsWith(ext) } }
                    .toList())
        }
        files ? files.max { Files.getLastModifiedTime(it) } : null
    }

    private static boolean isStable(Path file) {
        try {
            Files.size(file) > 0L
        } catch (IOException ignored) {
            false
        }
    }
}
