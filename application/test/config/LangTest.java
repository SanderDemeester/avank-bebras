
package config;

import java.util.Set;
import java.util.TreeSet;

import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import play.i18n.Messages;
import play.i18n.Lang;

import test.ContextTest;

/**
 * This class checks if all languages set to available in the config file are
 * actually avialable in the messages. It assumes a messages.xx file for each
 * language xx in application.conf, and a file called "messages" to the
 * default language.
 *
 * If this test succeeds, it start checking for each language if the messages
 * file is conform with the standard file. As such, it will warn you for missing
 * messages.
 * @author Felix Van der Jeugt
 */
public class LangTest extends ContextTest {

    private static Path base = Paths.get("conf");

    @Test public void checkFiles() {
        Set<String> files = new TreeSet<String>();
        for(String file : base.toFile().list()) {
            if(file.startsWith("messages")) files.add(file);
        }
        Assert.assertTrue(files.contains("messages"));

        for(Lang l : Lang.availables()) {
            String file = "messages." + l.code();
            Assert.assertTrue("The " + file + " is not to be seen.",
                    files.contains(file));
        }
    }

    @Test public void checkMessages() {
        BufferedReader reader = null;

        // Open messages file.
        try {
            Path m = base.resolve("messages");
            m = base.resolve(Files.readSymbolicLink(m));
            reader = Files.newBufferedReader(m, Charset.defaultCharset());
        } catch(IOException e) {
            Assert.fail("Unable to open messages file: " + e.getMessage());
        }

        // Open other messages files.
        BufferedReader[] readers = new BufferedReader[Lang.availables().size()];
        String[] names = new String[readers.length];
        int i = 0;
        for(Lang l : Lang.availables()) {
            String file = "messages." + l.code();
            readers[i] = null;
            names[i] = file;
            try {
                readers[i] = Files.newBufferedReader(base.resolve(file),
                        Charset.defaultCharset());
            } catch(IOException e) {
                Assert.fail("IO Fail (" + names[i] + "): " + e.getMessage());
            }
            i++;
        }

        // Start testing.
        String example = nextLine(reader);
        while(example != null) {
            example = example.split("=")[0].trim();
            for(i = 0; i < readers.length; i++) {
                Assert.assertTrue(
                    "Files " + names[i] + " does not have a " + example,
                    nextLine(readers[i]).startsWith(example)
                );
            }
            example = nextLine(reader);
        }
    }

    private String nextLine(BufferedReader reader) {
        String line = null;
        while(true) {
            try { line = reader.readLine(); }
            catch(IOException e) { Assert.fail("Couldn't read the line :-("); }

            if(line == null || !(line.startsWith("#") || line.equals(""))) {
                return line;
            }
        }
    }

}
