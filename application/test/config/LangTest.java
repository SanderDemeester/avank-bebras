
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
        try {
            Path m = Files.readSymbolicLink(base.resolve("messages"));
            reader = Files.newBufferedReader(m, Charset.defaultCharset());
            String line = reader.readLine();
            while(line != null) {
                if("".equals(line) || line.startsWith("#")) {
                    // Nothing
                } else {
                    String mess = line.split("=")[0].trim();
                    for(Lang lang : Lang.availables()) {
                        Assert.assertNotNull("Failed to retrieve " + mess +
                                " in " + lang.language() + ".",
                                Messages.get(lang, mess));
                    }
                }
                line = reader.readLine();
            }
        } catch(IOException e) {
            Assert.fail("Fail IO: " + e.getMessage());
        } catch(Exception e) {
            Assert.fail("Sad: " + e.getMessage());
        } finally {
            try {
                if(reader != null) reader.close();
            } catch(IOException e) { 
                Assert.fail("Fail IO close: " + e.getMessage());
            }
        }
    }

}
